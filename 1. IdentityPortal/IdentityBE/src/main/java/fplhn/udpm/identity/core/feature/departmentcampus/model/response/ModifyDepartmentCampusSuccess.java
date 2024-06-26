package fplhn.udpm.identity.core.feature.departmentcampus.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyDepartmentCampusSuccess {

    private Long departmentCampusId;

    private String departmentName;

    private String campusName;

    private String headDepartmentCampusName;

}
