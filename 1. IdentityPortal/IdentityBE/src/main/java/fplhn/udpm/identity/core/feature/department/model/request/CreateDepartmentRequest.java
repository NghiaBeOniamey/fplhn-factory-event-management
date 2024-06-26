package fplhn.udpm.identity.core.feature.department.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDepartmentRequest {

    @NotNull(message = "Tên bộ môn không được để trống")
    @NotBlank(message = "Tên bộ môn giữa khoảng giống không được cách quá 2 dấu cách")
    private String departmentName;

    @NotNull(message = "Mã bộ môn không được để trống")
    @NotBlank(message = "Mã bộ môn giữa khoảng giống không được cách quá 2 dấu cách")
    private String departmentCode;
}
