package fplhn.udpm.identity.core.feature.role.model.response;

import fplhn.udpm.identity.core.common.IsIdentify;
import fplhn.udpm.identity.core.common.HasOrderNumber;

public interface RoleResponse extends IsIdentify, HasOrderNumber {

    String getRoleCode();

    String getRoleName();

    String getRoleStatus();

}
