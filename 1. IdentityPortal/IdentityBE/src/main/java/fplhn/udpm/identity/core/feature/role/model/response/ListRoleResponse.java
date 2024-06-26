package fplhn.udpm.identity.core.feature.role.model.response;

import fplhn.udpm.identity.core.common.IsIdentify;

public interface ListRoleResponse extends IsIdentify {

    String getRoleCode();

    String getRoleName();

}
