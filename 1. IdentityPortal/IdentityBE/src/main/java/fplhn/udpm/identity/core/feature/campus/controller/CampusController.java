package fplhn.udpm.identity.core.feature.campus.controller;

import fplhn.udpm.identity.core.feature.campus.model.request.CampusPaginationRequest;
import fplhn.udpm.identity.core.feature.campus.model.request.ModifyCampusRequest;
import fplhn.udpm.identity.core.feature.campus.service.CampusService;
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
@RequestMapping(ApiConstant.API_CAMPUS_PREFIX)
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
@Hidden
public class CampusController {

    private final CampusService campusService;

    @GetMapping
    public ResponseEntity<?> get(CampusPaginationRequest request) {
        return Helper.createResponseEntity(campusService.getAllCampus(request));
    }

    @PostMapping
    public ResponseEntity<?> createCampus(@Valid @RequestBody ModifyCampusRequest modifyCampusRequest) {
        return Helper.createResponseEntity(campusService.createCampus(modifyCampusRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCampus(
            @Valid @RequestBody ModifyCampusRequest modifyCampusRequest,
            @PathVariable Long id
    ) {
        return Helper.createResponseEntity(campusService.updateCampus(modifyCampusRequest, id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateCampusStatus(@PathVariable Long id) {
        return Helper.createResponseEntity(campusService.updateCampusStatus(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllCampusSearch() {
        return Helper.createResponseEntity(campusService.getListCampus());
    }

}
