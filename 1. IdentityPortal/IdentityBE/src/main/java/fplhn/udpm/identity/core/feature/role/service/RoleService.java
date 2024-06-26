package fplhn.udpm.identity.core.feature.role.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.role.model.request.RolePaginationRequest;
import fplhn.udpm.identity.core.feature.role.model.request.ModifyRoleRequest;

public interface RoleService {

    ResponseObject findAllEntity(RolePaginationRequest request);

    ResponseObject create(ModifyRoleRequest request);

    ResponseObject update(ModifyRoleRequest request, Long id);

    ResponseObject delete(Long id);

    ResponseObject getRoleById(Long id);

    ResponseObject getAllRole();

}
