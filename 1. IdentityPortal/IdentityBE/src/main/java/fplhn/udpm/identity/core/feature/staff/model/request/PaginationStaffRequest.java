package fplhn.udpm.identity.core.feature.staff.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PaginationStaffRequest extends PageableRequest {

    private String staffName;

    private String staffCode;

    private String campusCode;

}
