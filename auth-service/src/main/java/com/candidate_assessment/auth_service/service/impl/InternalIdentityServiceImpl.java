package com.candidate_assessment.auth_service.service.impl;

import com.candidate_assessment.auth_service.constants.ErrorConstants;
import com.candidate_assessment.auth_service.dto.CreateIdentityRequest;
import com.candidate_assessment.auth_service.dto.CreateIdentityResponse;
import com.candidate_assessment.auth_service.entity.Role;
import com.candidate_assessment.auth_service.entity.User;
import com.candidate_assessment.auth_service.exception.RoleNotFoundException;
import com.candidate_assessment.auth_service.exception.UserAlreadyExistsException;
import com.candidate_assessment.auth_service.repository.RoleRepository;
import com.candidate_assessment.auth_service.repository.UserRepository;
import com.candidate_assessment.auth_service.security.PasswordManager;
import com.candidate_assessment.auth_service.service.InternalIdentityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InternalIdentityServiceImpl implements InternalIdentityService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordManager passwordManager;

    @Transactional
    @Override
    public CreateIdentityResponse createIdentity(CreateIdentityRequest request) {


          validateUniqueEmail(request.getEmail());

          Set<Role> roles = resolveRoles(request.getRoles());

          User user = buildUser(request,roles);

          User savedUser =  userRepository.save(user);

          return CreateIdentityResponse.builder()
                  .userId(savedUser.getId())
                  .build();

    }

    private User buildUser(CreateIdentityRequest request, Set<Role> roles) {


       return User.builder()
                .email(request.getEmail())
                .passwordHash(passwordManager.encode(request.getPassword()))
                .displayName(request.getDisplayName())
                .roles(roles)
                .enabled(true)
                .accountNonLocked(true)
                .createdAt(LocalDateTime.now())
                .build();

    }

    private Set<Role> resolveRoles(Set<String> roles) {

        return roles.stream()
                .map(name -> roleRepository.findByRoleName(name)
                        .orElseThrow(() -> new RoleNotFoundException(ErrorConstants.ROLE_NOT_FOUND + name)))
                  .collect(Collectors.toSet());

    }

    private void validateUniqueEmail(String email) {

        if(userRepository.existsByEmail(email))
            throw new UserAlreadyExistsException(ErrorConstants.EMAIL_ALREADY_EXISTS);

    }
}
