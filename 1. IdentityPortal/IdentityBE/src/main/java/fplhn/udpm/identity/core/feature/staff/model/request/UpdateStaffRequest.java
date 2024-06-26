package fplhn.udpm.identity.core.feature.staff.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UpdateStaffRequest {

    @NotEmpty(message = "Tên nhân viên không được để trống")
    private String staffName;

    private Long departmentId;

    private Long campusId;

    @NotEmpty(message = "Email FPT không được để trống")
    @Email(regexp = "^[a-zA-Z0-9._-]+@fpt.edu.vn$", message = "Email FPT không đúng định dạng")
    private String emailFpt;

    @NotEmpty(message = "Email FE không được để trống")
    @Email(regexp = "^[a-zA-Z0-9._-]+@fe.edu.vn$", message = "Email FE không đúng định dạng")
    private String emailFe;

    private String phoneNumber;

}
