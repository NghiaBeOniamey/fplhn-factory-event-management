package fplhn.udpm.identity.core.feature.departmentcampus.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModifyDepartmentCampusRequest {

    @NotNull(message = "Id bộ môn không được để trống")
    private Long departmentId;

    @NotNull(message = "Id cơ sở không được để trống")
    private Long campusId;

    @NotNull(message = "Id CNBM không được để trống")
    private Long headDepartmentCampusId;

}
