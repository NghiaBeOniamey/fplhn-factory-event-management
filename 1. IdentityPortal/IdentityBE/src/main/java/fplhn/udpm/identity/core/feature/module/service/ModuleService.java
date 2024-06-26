package fplhn.udpm.identity.core.feature.module.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.module.model.request.CreateModuleRequest;
import fplhn.udpm.identity.core.feature.module.model.request.ModulePaginationRequest;
import fplhn.udpm.identity.core.feature.module.model.request.UpdateModuleRequest;
import org.springframework.stereotype.Service;

@Service
public interface ModuleService {

    ResponseObject getAllModule(ModulePaginationRequest request);

    ResponseObject getListModule();

    ResponseObject createModule(CreateModuleRequest createModuleRequest);

    ResponseObject updateModule(UpdateModuleRequest updateModuleRequest, Long id);

    ResponseObject updateStatusModule(Long id);

    ResponseObject getClientByModuleId(Long id);

}
