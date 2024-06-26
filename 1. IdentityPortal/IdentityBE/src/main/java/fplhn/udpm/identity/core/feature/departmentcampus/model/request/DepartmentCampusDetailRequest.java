package fplhn.udpm.identity.core.feature.departmentcampus.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DepartmentCampusDetailRequest extends PageableRequest {

    private Long[] searchValues;

    private String departmentStatus;

}
