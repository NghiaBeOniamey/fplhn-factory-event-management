package com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone;

public interface AdminHOeeEventExcelResponse {

    String getCampusName(); // Cơ sở

    String getDepartmentName(); // Bộ môn

    String getMajorName(); // Chuyên ngành

    String getEventName(); // Tên sự kiện

    String getGoalOfEvent(); // Mục tiêu sự kiện

    String getTypeOfEvent(); // Loại sự kiện

    Long getStartTime(); // Thời gian bắt đầu

    Long getEndTime(); // Thời gian kết thúc

    Double getMeetingHours(); // Số giờ

    String getGuestOfEvent(); // Khách mời/doanh nghiệp

    Long getExpectedNumberOfStudents(); // SL sinh viên tham gia dự kiến

    Long getNumberOfLectures(); // SL giảng viên tham gia tổ chức

    Double getCostOfEvent(); // Kinh phí sự kiện

    Long getNumberOfStudents(); // SL sinh viên tham gia

    Integer getFormality(); // Hình thức

    String getOrganizationLocation(); // Địa điểm tổ chức

    String getPathOnline(); // Link Zoom, Meet

    String getPathOffline(); // Link Zoom, Meet

    String getEventPostingLink(); // Link đăng sự kiện, Poster (nếu có)

    String getLinkFeedback(); // Link Feedback (nếu có)

    String getDescription(); // Mô tả

    Integer getStatus(); // Trạng thái

}
