package com.apigateway.constants;

public class LogConstants {

    private LogConstants() {}

    public static final String LOG_INTERNAL_ERROR = "Internal error at path {} and Method {}";
    public static final String LOG_CLIENT_ERROR = "Client error at path : {} and  Method : {} and Message: {}";
    public static final String IP_RATE_LIMIT_EXCEEDED = "IP rate limit exceeded for key = {}";

    public static final String USER_RATE_LIMIT_EXCEEDED = "User rate limit exceeded for userId = {}";


}
