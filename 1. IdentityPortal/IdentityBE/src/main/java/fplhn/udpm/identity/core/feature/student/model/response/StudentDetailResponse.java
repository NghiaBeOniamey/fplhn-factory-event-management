package fplhn.udpm.identity.core.feature.student.model.response;

public interface StudentDetailResponse {

    Long getStudentId();

    String getStudentCode();

    String getStudentName();

    String getStudentStatus();

    String getStudentMail();

    String getStudentPhoneNumber();

    Long getDepartmentId();

    Long getCampusId();

}
