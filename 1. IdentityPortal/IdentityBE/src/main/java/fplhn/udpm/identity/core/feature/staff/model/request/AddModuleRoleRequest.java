package fplhn.udpm.identity.core.feature.staff.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AddModuleRoleRequest {

    @NotNull
    private Long staffId;

    @NotNull
    private List<Long> moduleId;

    @NotNull
    private List<Long> roleId;

}
