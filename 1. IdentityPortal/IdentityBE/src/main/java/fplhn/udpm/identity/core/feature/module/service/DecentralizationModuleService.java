package fplhn.udpm.identity.core.feature.module.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.module.model.request.ModuleRoleStaffRequest;
import fplhn.udpm.identity.core.feature.module.model.request.ModuleRoleStaffUpdateRequest;

public interface DecentralizationModuleService {

    ResponseObject getListRoleAvailable();

    ResponseObject getStaffRoleModule(ModuleRoleStaffRequest request);

    ResponseObject modifyStaffRoleModule(ModuleRoleStaffUpdateRequest request);

    ResponseObject getListStaffInfo(String staffCode);

}
