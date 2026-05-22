package com.candidate_assessment.auth_service.service.impl;

import com.candidate_assessment.auth_service.config.JwtConfig;
import com.candidate_assessment.auth_service.constants.ErrorConstants;
import com.candidate_assessment.auth_service.constants.SecurityConstants;
import com.candidate_assessment.auth_service.dto.JwtUserContext;
import com.candidate_assessment.auth_service.dto.LoginRequest;
import com.candidate_assessment.auth_service.dto.LoginResponse;
import com.candidate_assessment.auth_service.entity.Permission;
import com.candidate_assessment.auth_service.entity.Role;
import com.candidate_assessment.auth_service.entity.User;
import com.candidate_assessment.auth_service.exception.InvalidCredentialsException;
import com.candidate_assessment.auth_service.repository.RoleRepository;
import com.candidate_assessment.auth_service.repository.UserRepository;
import com.candidate_assessment.auth_service.security.PasswordManager;
import com.candidate_assessment.auth_service.service.AuthService;
import com.candidate_assessment.auth_service.service.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final JwtConfig jwtConfig;

    private final PasswordManager passwordManager;


    @Override
    public LoginResponse login(LoginRequest request) {

        Set<String> roles = new HashSet<>();
        Set<String>  permissions = new HashSet<>();

        User user = findByEmail(request.getEmail());

        matchPassword(request,user.getPasswordHash());

        List<Set<String>> list = extractRolesAndPermissions(roles,permissions,user);

        JwtUserContext jwtUserContext = buildJwtContext(user, roles, permissions);

        String accessToken = jwtService.generateAccessToken(jwtUserContext);

        return  buildLoginResponse(user, accessToken);

    }


    private JwtUserContext buildJwtContext(User user, Set<String> roles, Set<String> permissions) {
        return JwtUserContext.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .roles(roles)
                .permissions(permissions)
                .build();
    }

    private LoginResponse buildLoginResponse(User user,String accessToken) {


           return LoginResponse.builder()
                   .accessToken(accessToken)
                   .displayName(user.getDisplayName())
                   .tokenType(SecurityConstants.BEARER)
                   .expiresIn(jwtConfig.getAccessTokenExpiration())
                   .build();

    }

    private List<Set<String>> extractRolesAndPermissions(Set<String> roles, Set<String> permissions,User user) {


        user.getRoles().forEach(role -> {

            roles.add(role.getRoleName());

            role.getPermissions().forEach(p -> {

                permissions.add(p.getPermissionName());

            });

        });

        return List.of(roles, permissions);

    }

    private void matchPassword(LoginRequest request, String password) {

        if (! passwordManager.matches(request.getPassword(), password)) {

            throw new InvalidCredentialsException(ErrorConstants.INVALID_CREDENTIALS);

        }
    }

    private User findByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new InvalidCredentialsException(ErrorConstants.INVALID_CREDENTIALS));

    }


}
