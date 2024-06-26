package fplhn.udpm.identity.core.feature.student.model.response;

import fplhn.udpm.identity.core.common.HasOrderNumber;

public interface StudentResponse extends HasOrderNumber {

    Long getStudentId();

    String getStudentCode();

    String getStudentName();

    String getStudentStatus();

    String getStudentMail();

    String getStudentPhoneNumber();

    String getDepartmentCampusId();

    String getDepartmentNameAndCampusName();

}
