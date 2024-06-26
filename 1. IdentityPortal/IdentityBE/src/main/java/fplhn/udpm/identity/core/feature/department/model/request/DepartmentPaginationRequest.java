package fplhn.udpm.identity.core.feature.department.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentPaginationRequest extends PageableRequest {

    private String xoaMemBoMon;

    private Long[] searchValues;

}
