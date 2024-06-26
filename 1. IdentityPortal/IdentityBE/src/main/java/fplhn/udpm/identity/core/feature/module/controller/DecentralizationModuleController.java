package fplhn.udpm.identity.core.feature.module.controller;

import fplhn.udpm.identity.core.feature.module.model.request.ModuleRoleStaffRequest;
import fplhn.udpm.identity.core.feature.module.model.request.ModuleRoleStaffUpdateRequest;
import fplhn.udpm.identity.core.feature.module.service.DecentralizationModuleService;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.util.Helper;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_DECENTRALIZATION_MODULE_PREFIX)
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@Hidden
public class DecentralizationModuleController {

    private final DecentralizationModuleService decentralizationModuleService;

    @GetMapping("/get-list-role-available")
    public ResponseEntity<?> getListRoleAvailable() {
        return Helper.createResponseEntity(decentralizationModuleService.getListRoleAvailable());
    }

    @GetMapping("/get-staff-role-module")
    public ResponseEntity<?> getStaffRoleByIdModule(ModuleRoleStaffRequest request) {
        return Helper.createResponseEntity(decentralizationModuleService.getStaffRoleModule(request));
    }

    @PostMapping("/update-staff-role-module")
    public ResponseEntity<?> updateStaffRoleByIdModule(@RequestBody @Valid ModuleRoleStaffUpdateRequest request) {
        return Helper.createResponseEntity(decentralizationModuleService.modifyStaffRoleModule(request));
    }

    @GetMapping("/get-list-staff-info-search")
    public ResponseEntity<?> getListStaffInfo(String staffCode) {
        return Helper.createResponseEntity(decentralizationModuleService.getListStaffInfo(staffCode));
    }

}
