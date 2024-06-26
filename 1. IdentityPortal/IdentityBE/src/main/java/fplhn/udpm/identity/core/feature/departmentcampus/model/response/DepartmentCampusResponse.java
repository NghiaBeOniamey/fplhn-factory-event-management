package fplhn.udpm.identity.core.feature.departmentcampus.model.response;

import fplhn.udpm.identity.core.common.HasOrderNumber;
import fplhn.udpm.identity.core.common.IsIdentify;

public interface DepartmentCampusResponse extends IsIdentify, HasOrderNumber {

    Long getDepartmentCampusId();

    Long getCampusId();

    Long getHeadDepartmentCampusId();

    String getCampusName();

    String getCampusCode();

    String getHeadDepartmentCampusName();

    String getDepartmentCampusStatus();

    String getHeadDepartmentCampusCode();

}
