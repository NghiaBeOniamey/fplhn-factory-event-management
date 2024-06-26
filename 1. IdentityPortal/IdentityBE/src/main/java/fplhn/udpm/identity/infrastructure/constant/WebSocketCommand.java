package fplhn.udpm.identity.infrastructure.constant;

public class WebSocketCommand {

    public static final String CHANGE_STATUS = "CHANGE_STATUS";

    public static final String CHANGE_ROLE = "CHANGE_ROLE";

    public static final String IMPORT_STAFF_DONE = "IMPORT_STAFF_DONE";

    public static final String IMPORT_ROLE_DONE = "IMPORT_ROLE_DONE";

    public static String getChangeStatusUser(Long userId) {
        return CHANGE_STATUS + "_" + userId;
    }

    public static String getChangeRoleUser(Long userId) {
        return CHANGE_ROLE + "_" + userId;
    }

    public static String getImportStaffDone(Long userId) {
        return IMPORT_STAFF_DONE + "_" + userId;
    }

    public static String getImportRoleDone(Long userId) {
        return IMPORT_ROLE_DONE + "_" + userId;
    }

}
