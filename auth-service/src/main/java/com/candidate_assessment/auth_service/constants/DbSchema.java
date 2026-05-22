package com.candidate_assessment.auth_service.constants;


public final class DbSchema {

    // Prevent instantiation
    private DbSchema() {}

    // --- USER DOMAIN ---
    public static final String TABLE_USERS = "users";
    public static final String COL_DISPLAY_NAME = "displayName";
    public static final String COL_USER_EMAIL = "email";
    public static final String COL_USER_LOCKED = "account_non_locked";


    public static final int LENGTH_USERNAME = 100;
    public static final int LENGTH_EMAIL = 100;


    // --- ROLE & PERMISSION DOMAIN ---
    public static final String TABLE_ROLES = "roles";
    public static final String COL_ROLE_NAME = "role_name";

    public static final String TABLE_PERMISSIONS = "permissions";
    public static final String COL_PERM_NAME = "permission_name";


    // --- JOIN TABLES & RELATIONSHIP MAPPINGS ---
    public static final String JOIN_TABLE_USER_ROLES = "user_roles";
    public static final String JOIN_TABLE_ROLE_PERM = "role_permissions";


    // Foreign Key Column Names for Join Tables
    public static final String JOIN_COL_USER = "user_id";
    public static final String JOIN_COL_ROLE = "role_id";
    public static final String JOIN_COL_PERM = "permission_id";

    // --- JPA SPECIFIC FIELDS (mappedBy, MapsId, EntityGraphs) ---

    public static final String FIELD_ROLES = "roles";
    public static final String FIELD_PERMISSIONS = "permissions";

    // Composite Key Fields (Match field names in Embeddable classes)

    // EntityGraph Paths
    public static final String PATH_ROLES_PERMISSIONS = FIELD_ROLES + "." + FIELD_PERMISSIONS;

}