package com.apigateway.config;

import com.apigateway.constants.SecurityConstants;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;


@ConfigurationProperties(SecurityConstants.SECURITY_CONFIG_PREFIX)
@Setter
@Getter
public class SecurityProperties {

    private List<String> publicPaths = List.of();
    private String jwtSecret;

    private List<RoleMapping> roleMappings = List.of();

    @Data
    public static class RoleMapping {
        private String path;
        private Map<String, List<String>> methods;
    }

}
