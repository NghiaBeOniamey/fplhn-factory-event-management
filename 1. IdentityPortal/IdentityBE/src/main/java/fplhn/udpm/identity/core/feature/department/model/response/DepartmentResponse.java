package fplhn.udpm.identity.core.feature.department.model.response;

import fplhn.udpm.identity.core.common.IsIdentify;
import fplhn.udpm.identity.core.common.HasOrderNumber;

public interface DepartmentResponse extends IsIdentify, HasOrderNumber {

    Long getDepartmentId();

    String getDepartmentCode();

    String getDepartmentName();

    String getDepartmentStatus();

}
