package fplhn.udpm.identity.core.feature.module.controller;

import fplhn.udpm.identity.core.feature.module.model.request.CreateModuleRequest;
import fplhn.udpm.identity.core.feature.module.model.request.ModulePaginationRequest;
import fplhn.udpm.identity.core.feature.module.model.request.UpdateModuleRequest;
import fplhn.udpm.identity.core.feature.module.service.ModuleService;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.util.Helper;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiConstant.API_MODULE_PREFIX)
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@Hidden
public class ModuleController {

    private final ModuleService moduleService;

    @GetMapping
    public ResponseEntity<?> getModuleByListId(ModulePaginationRequest request) {
        return Helper.createResponseEntity(moduleService.getAllModule(request));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getListModule() {
        return Helper.createResponseEntity(moduleService.getListModule());
    }

    @PostMapping
    public ResponseEntity<?> addCoSo(@Valid @RequestBody CreateModuleRequest createModuleRequest) {
        return Helper.createResponseEntity(moduleService.createModule(createModuleRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateModule(@RequestBody UpdateModuleRequest request, @PathVariable Long id) {
        return Helper.createResponseEntity(moduleService.updateModule(request, id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> deleteModule(@PathVariable Long id) {
        return Helper.createResponseEntity(moduleService.updateStatusModule(id));
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<?> getClientByModuleId(@PathVariable Long id) {
        return Helper.createResponseEntity(moduleService.getClientByModuleId(id));
    }

}
