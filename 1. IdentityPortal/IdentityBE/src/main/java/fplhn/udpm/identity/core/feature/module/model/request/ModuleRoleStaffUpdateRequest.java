package fplhn.udpm.identity.core.feature.module.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ModuleRoleStaffUpdateRequest {

    @NotNull
    private Long moduleId;

    private List<StaffRoleRequest> staffs;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class StaffRoleRequest {

        private Long staffId;

        private String roles;

    }

}
