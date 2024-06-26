package fplhn.udpm.identity.core.feature.staff.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PaginationStaffModuleRoleRequest extends PageableRequest {

    private String column;

    private Sort.Direction type;

}
