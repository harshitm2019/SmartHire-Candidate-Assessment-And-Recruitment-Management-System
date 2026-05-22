package com.candidate_assessment.auth_service.constants;

public class SecurityConstants {

    private SecurityConstants() {}


    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    public static final String ISSUER = "candidate-assessment-auth-service";

    public static final String TOKEN_TYPE_ACCESS = "ACCESS";

    public static final String USER_ID = "userId";
    public static final String ROLES = "roles";
    public static final String EMAIL = "email";
    public static final String COMPANY_ID =  "companyId";

    public static final String PERMISSIONS = "permissions";

    public static final String TOKEN_TYPE = "tokenType";

    public static final int PASSWORD_ENCODER_STRENGTH = 12;


}
