package com.portalevent.infrastructure.constant;

/**
 * @author SonPT
 */
public enum Message {

    SUCCESS("Success"),

    ERROR_UNKNOWN("Error Unknown"),

    EVENT_NOT_EXIST("Sự kiện không tồn tại"),
    INVALID_EVENT_STATUS("Trạng thái sự kiện không hợp lệ"),
    PARTICIPANT_NOT_EXIST("Người tham gia không tồn tại"),
    PAGE_NOT_EXIST("Trang không tồn tại"),
    PARTICIPANT_NOT_IN_EVENT("Tài khoản chưa đăng ký tham gia sự kiện"),
    COMMENT_NOT_EXIST("Comment không tồn tại"),
    DONT_HAVE_PERMISSION_DELETE("Không có quyền xóa"),
    ROLL_CALL_NOT_OPEN("Điểm danh chưa mở"),
    CATEGORY_CODE_OVERLAPPED("Trùng mã thể loại"),
    INVALID_START_TIME("Thời gian bắt đầu không hợp lệ"),
    INVALID_END_TIME("Thời gian kết thúc không hợp lệ"),
    RESOURCE_NOT_EXIST("Tài nguyên này không tồn tại"),
    ID_MUST_NOT_EMPTY("ID không được trống"),
    APPROVED_DOES_NOT_EXIST("Người phê duyệt không tồn tại"),
    REASON_MUST_NOT_EMPTY("Lý do không được để trống"),
    SEMESTER_NOT_EXISTS("Học kỳ không tồn tại"),
    TIME_OF_EVENT_MUST_IN_TIME_OF_BLOCK("Thời gian diễn ra sự kiện phải nằm trong thời gian của Block"),
    INVALID_STATUS("Trạng thái sự kiện không hợp lệ"),
    EVENT_ORGANIZER_NOT_EXIST("Người tổ chức không tồn tại"),
    CAN_NOT_DELETE_HOST("Không thể xoá HOST"),
    AGENDA_NOT_EXIST("Agenda không tồn tại"),
    ORGANIZER_HAVE_AGENDA("Người tổ chức được phân công trong Agenda. Không thể xóa"),

    EVENT_NEED_HOST("Sự kiện cần phải có ít nhất 1 người chủ trì sự kiện"),
    EVENT_LOCATION_NOT_EXIT("Địa điểm không tồn tại"),
    YOU_HAVE_NO_RIGHTS("Không được quyền thao tác"),
    UPLOAD_IMAGE_FAIL("Upload ảnh thất bại"),
    DELETE_IMAGE_FAIL("Xóa ảnh thất bại"),
    EVIDENCE_NOT_EXIST("Evidence không tồn tại"),
    INCORRECT_STATUS("Trạng thái không hợp lệ"),
    EVENT_HAS_NOT_STARTED("Sự kiện chưa diễn ra"),
    INVALID_TOKEN("Token không hợp lệ"),
    ROLE_USER_CHANGE("Đã thay đổi quyền"),
    PARTICIPANT_ALREADY_EXIST("Bạn đã đăng ký sự tham gia sự kiện này"),
    REGISTRATION_NOT_OPEN("Đăng ký tham gia chưa mở"),
    LOCATION_NAME_ALREADY_EXIST("Tên địa điểm đã tồn tại"),
    RESOURCE_NAME_ALREADY_EXIST("Tên tài nguyên đã tồn tại"),
    EVIDENCE_NAME_ALREADY_EXIST("Tên evidence đã tồn tại"),
    CATEGORY_NAME_ALREADY_EXIST("Thể loại đã tồn tại"),
    OBJECT_NAME_ALREADY_EXSIT("Đối tượng đã tồn tại"),
    CATEGORY_NAME_LESS_THAN_100_CHARACTERS("Tên thể loại vượt quá 100 ký tự"),
    OBJECT_NAME_LESS_THAN_100_CHARACTERS("Tên đối tượng vượt quá 100 ký tự"),
    EVENT_LOCATION_ALREADY_EXIT("Địa điểm đã tồn tại"),
    PERIODIC_EVENT_NOT_EXISTS("Sự kiện hằng kỳ không tồn tại"),
    APPROVED_EVENT_EXIST_DURING_TIME_PERIOD("` của bạn đã được phê duyệt và trùng với thời gian diễn ra của sự kiện này"),
    EVENT_NAME_IS_REQUIRED("Tên sự kiện không được trống"),
    MAJOR_NOT_EXIST("Bộ môn không tồn tại"),
    OBJECT_NOT_EXIST("Đối tượng không tồn tại"),
    CATEGORY_NOT_EXIST("Thể loại không tồn tại"),
    SEMESTER_NAME_ALREADY_EXIST("Tên học kỳ đã tồn tại"),
    SEMESTER_NAME_LESS_THAN_100_CHARACTERS("Tên học kỳ vượt quá 100 ký tự"),
    SEMESTER_IS_ALREADY_IN_EVENT_EXIT("Có sự kiện diễn ra trong học kỳ này. Không thể xóa"),
    INVITATION_TIME_NOT_EXIST("Lịch gửi thư mời không tồn tại"),
    EXCEEDING_START_TIME("Vượt quá thời gian bắt đầu sự kiện"),
    SCHEDULE_FEATURE_ONLY("Chỉ có thể đặt lịch trong tương lai"),
    SCHEDULE_FAILURE("Đặt lịch gửi thư mời thất bại"),
    NOTHING_TO_SAVE("Không có thay đổi"),
    INVITATION_HAS_BEEN_SENT("Thư mời đã được gửi"),
    CANNOT_DELETED_PARENT_MAJOR("Không thể xóa bộ môn cha đã có bộ môn con"),
    CANNOT_DELETED_MAJOR_IN_EVENT("Có sự kiện diễn ra trong bộ môn này. Không thể xóa"),
    EMAIL_ALREADY_EXISTS("Email đã tồn tại"),
    MAJOR_CODE_ALREADY_EXISTS("Mã bộ môn đã tồn tại"),
    MAJOR_NAME_ALREADY_EXISTS("Tên bộ môn đã tồn tại"),
    SESSION_EXPIRED("session-expired"),
    ROLE_HAS_CHANGE("role-has-change"),
    LECTURER_IS_REQURIED("Không được bỏ trống giảng viên"),
    CLASS_NAME_IS_REQUIRED("Không được bỏ trống tên lớp"),
    UPDATE_ALL_EVENT_INFORMATION("Cần cập nhật thông tin cho sự kiện"),
    INVALID_TIME_FIRST_BLOCK("Thời gian bắt đầu block 1 không hợp lệ"),
    INVALID0_TIME_SECONCD_BLOCK("Thời gian bắt đầu block 2 không hợp lệ"),
    OVERLAP_SEMESTER_TIME("Thời gian diễn ra học kỳ trùng với học kỳ: "),
    THE_ORGANIER_ALREADY_EXISTS("Người tổ chức đã tồn tại"),
    UNDEFINED_SYSTEM_OPTION("System option undefined"),
    INVALID_MANDATORY_APPROVAL_DAYS("Invalid mandatory approval days"),
    APPROVAL_TIME_INVALID("Thời gian gửi yêu cầu phê duyệt không hợp lệ"),
    SEND_MAIL_FAILED("Gửi mail thất bại"),
    INVALID_USER("Người dùng không tồn tại"),
    EMAIL_HAS_BEEN_SEND("Thư mời đã được gửi. Không thể xóa"),
    SEND_CONVERSION_REQUEST_FAIL("Gửi yêu cầu quy đổi thất bại!"),
    SYSTEM_OPTION_NOT_FOUND("Không tìm thấy tùy chỉnh hệ thống"),
    OVERLAP_ORGANIZER("Sự kiện trung người tổ chức"),
    INVALID_FILE("File không đúng định dạng"),
    SIZE_FILE("File qua dung lượng cho phép"),
    INVALID_EXCEL_FORMAT("File excel không đúng định dạng"),
    FILE_IS_NOT_EMPTY("File excel trống"),
    INVALID_DATA_FILE("Dữ liệu file sai định dạng"),
    TIME_REGISTER_TO_PARTICIPATE_HAS_EXPIRED("Đã hết thời gian đang ký tham gia sự kiện"),
    INVALID_NUMERIC_CELL_VALUE("Lỗi định dạng cột số tại cột Numeric");


    private String message;

    Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
