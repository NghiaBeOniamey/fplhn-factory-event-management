package fplhn.udpm.identity.core.feature.module.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleRoleStaffRequest extends PageableRequest {

    private Long moduleId;

    private String listStaffCode;

}
