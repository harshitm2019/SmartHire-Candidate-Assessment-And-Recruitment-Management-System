package com.apigateway.service;


import com.apigateway.config.SecurityProperties;
import com.apigateway.exception.AuthException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final SecurityProperties securityProperties;

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    private final Map<String, Map<String, Set<String>>> permissionCache = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){

        for (SecurityProperties.RoleMapping mapping :
                securityProperties.getRoleMappings()) {

            Map<String, Set<String>> methodsMap =
                    new ConcurrentHashMap<>();

            mapping.getMethods()
                    .forEach((method, permissions) ->
                            methodsMap.put(
                                    method,
                                    Set.copyOf(permissions)
                            ));

            permissionCache.put(
                    mapping.getPath(),
                    methodsMap
            );
        }
    }

    public void validateAccess(String path, String method, List<String> userPermissions) {

        for (Map.Entry<String, Map<String, Set<String>>> entry :
                permissionCache.entrySet()) {

            if (pathMatcher.match(entry.getKey(), path)){

                Set<String> requiredPermissions =
                        entry.getValue().get(method);

                if (requiredPermissions == null ||
                        requiredPermissions.isEmpty()) {

                    throw AuthException.methodNotAllowed();
                }

                boolean allowed =
                        requiredPermissions.stream()
                                .anyMatch(userPermissions::contains);

                if (!allowed) {
                    throw AuthException.insufficientPermissions();
                }

                return;
            }

        }

        throw AuthException.noRoutePermissionDefined();

    }

}