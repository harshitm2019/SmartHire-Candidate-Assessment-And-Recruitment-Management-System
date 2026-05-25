package com.apigateway.constants;

public class SecurityConstants {

    private SecurityConstants() {}

    public static final String AUTH_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    public static final String HEADER_USER_ID = "X-User-Id";
    public static final String HEADER_ROLES = "X-Roles";
    public static final String HEADER_PERMISSIONS = "X-Permissions";

    public static final String USER_PREFIX = "USER_";
    public static final String IP_PREFIX = "IP_";

    public static final String SECURITY_CONFIG_PREFIX = "app.security";

    public static final String CLAIM_USER_ID = "userId";
    public static final String CLAIM_ROLE = "role";
    public static final String CLAIM_PERMISSIONS = "permissions";

    public static final String IP_LIMIT = "IP_LIMIT_";
    public static final String HEADER_REMAINING = "X-Rate-Limit-Remaining";
    public static final String HEADER_RETRY_AFTER = "X-Rate-Limit-Retry-After-Seconds";
    public static final String HEADER_USER_REMAINING = "X-User-Rate-Limit-Remaining";
    public static final String ERROR_MSG_TOO_MANY_REQUESTS = "Too many requests";


}
