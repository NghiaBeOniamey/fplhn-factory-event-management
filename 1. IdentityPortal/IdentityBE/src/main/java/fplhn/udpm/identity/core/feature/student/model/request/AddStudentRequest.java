package fplhn.udpm.identity.core.feature.student.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddStudentRequest {

    @NotNull
    private String studentCode;

    @NotNull
    private String studentName;

    private String studentMail;

    private String studentPhoneNumber;

    private Long departmentId;

    private Long campusId;

}
