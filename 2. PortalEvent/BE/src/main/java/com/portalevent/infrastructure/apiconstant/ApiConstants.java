package com.portalevent.infrastructure.apiconstant;

/**
 * @author thangncph26123
 */
public class ApiConstants {

    // API Indentity

    public static final String API_GET_USER_BY_LIST_ID = "/api/connector/get-detail-users";

    public static final String API_GET_USER_BY_ID = "/api/connector/get-user-by-id";

    public static final String API_GET_ALL_USER_BY_ROLE_AND_MODULE = "/api/connector/get-list-user-by-role-code";

    public static final String API_GET_ROLES_USER_BY_ID_USER_AND_MODULE_CODE = "/api/connector/get-roles-of-user";

    // API Consumer

    public static final String API_READ_FILE_LOG = "/api/rabbit-consumer/read-log/page";

    public static final String API_DOWLOAD_FILE_LOG = "/api/rabbit-consumer/download-log";

    public static final String API_REPORT_BUG = "/api/support/mail";

    //API Honey
//    public static final String API_GET_HONEY_CATEGORY = "/api/add-point-student/list-category";
//
//    public static final String API_SEND_CONVERSION_HONEY = "/api/add-point-student/portal-events";
}

