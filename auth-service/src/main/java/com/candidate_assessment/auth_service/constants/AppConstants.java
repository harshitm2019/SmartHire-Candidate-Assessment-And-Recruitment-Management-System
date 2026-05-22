package com.candidate_assessment.auth_service.constants;

public class AppConstants {

    private AppConstants() {}

    public static final int MIN_USERNAME = 4;
    public static final int MAX_USERNAME = 50;

    public static final int MIN_PASSWORD = 8;
    public static final int MAX_PASSWORD = 100;

    // --- Error Messages ---
    public static final String MSG_DISPLAY_NAME_REQUIRED = "Display Name is required";
    public static final String MSG_USERNAME_SIZE = "Username must be between " + MIN_USERNAME + " and " + MAX_USERNAME + " characters";

    public static final String MSG_EMAIL_REQUIRED = "Email is required";
    public static final String MSG_EMAIL_INVALID = "Invalid email format";

    public static final String MSG_PASSWORD_REQUIRED = "Password is required";
    public static final String MSG_PASSWORD_SIZE = "Password must be between " + MIN_PASSWORD + " and " + MAX_PASSWORD + " characters";

    public static final String COMPANY_NAME_REQUIRED = "Company name is required";

    public static final String COMPANY_ADMIN_ROLE = "COMPANY_ADMIN";

}
