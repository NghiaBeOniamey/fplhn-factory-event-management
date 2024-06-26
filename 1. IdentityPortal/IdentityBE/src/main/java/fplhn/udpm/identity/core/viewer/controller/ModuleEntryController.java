package fplhn.udpm.identity.core.viewer.controller;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.viewer.model.response.ModuleEntryResponse;
import fplhn.udpm.identity.core.viewer.repository.ModuleEntryRepository;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.util.Helper;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.API_ENTRY_MODULE_PREFIX)
@CrossOrigin("*")
@Slf4j
@Hidden
public class ModuleEntryController {

    private final ModuleEntryRepository moduleEntryRepository;

    @GetMapping("/all")
    public ResponseEntity<?> findAllModule() {
        List<ModuleEntryResponse> moduleEntryResponses = moduleEntryRepository.findAllModule();
        if (moduleEntryResponses.isEmpty()) {
            return Helper.createResponseEntity(new ResponseObject<>(null, HttpStatus.NOT_FOUND, "Module not found"));
        }
        return Helper.createResponseEntity(new ResponseObject<>(moduleEntryResponses, HttpStatus.OK, "Success"));
    }

}
