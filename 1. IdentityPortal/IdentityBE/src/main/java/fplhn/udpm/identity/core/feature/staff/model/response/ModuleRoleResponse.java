package fplhn.udpm.identity.core.feature.staff.model.response;

import java.util.List;

public record ModuleRoleResponse(
        List<ModuleResponse> listModule,
        List<RoleResponse> listRole
) {
}
