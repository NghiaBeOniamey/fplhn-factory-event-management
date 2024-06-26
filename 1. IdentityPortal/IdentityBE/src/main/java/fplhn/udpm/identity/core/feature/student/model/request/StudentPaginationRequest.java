package fplhn.udpm.identity.core.feature.student.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentPaginationRequest extends PageableRequest {

    private String campusCode;

    private String studentCode;

    private String studentName;

    private String studentMail;

    private Long[] listDepartmentId;

}
