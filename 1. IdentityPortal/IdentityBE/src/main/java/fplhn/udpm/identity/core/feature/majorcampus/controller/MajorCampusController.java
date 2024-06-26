package fplhn.udpm.identity.core.feature.majorcampus.controller;

import fplhn.udpm.identity.core.feature.majorcampus.model.request.CreateMajorCampusRequest;
import fplhn.udpm.identity.core.feature.majorcampus.model.request.MajorCampusRequest;
import fplhn.udpm.identity.core.feature.majorcampus.model.request.UpdateMajorCampusRequest;
import fplhn.udpm.identity.core.feature.majorcampus.service.MajorCampusService;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.util.Helper;
import io.swagger.v3.oas.annotations.Hidden;
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
@RequiredArgsConstructor
@RequestMapping(ApiConstant.API_MAJOR_CAMPUS_PREFIX)
@CrossOrigin("*")
@Slf4j
@Hidden
public class MajorCampusController {

    private final MajorCampusService majorCampusService;

    @GetMapping
    public ResponseEntity<?> findAllMajorCampus(MajorCampusRequest request) {
        return Helper.createResponseEntity(majorCampusService.getAllMajorCampus(request));
    }

    @PostMapping
    public ResponseEntity<?> createMajorCampus(@RequestBody CreateMajorCampusRequest request) {
        return Helper.createResponseEntity(majorCampusService.addMajorCampus(request));
    }

    @PutMapping("/{majorCampusId}")
    public ResponseEntity<?> updateMajorCampus(@PathVariable Long majorCampusId, @RequestBody UpdateMajorCampusRequest request) {
        return Helper.createResponseEntity(majorCampusService.updateMajorCampus(majorCampusId, request));
    }

    @PutMapping("/status/{majorCampusId}")
    public ResponseEntity<?> changeStatusMajorCampus(@PathVariable Long majorCampusId) {
        return Helper.createResponseEntity(majorCampusService.changeStatusMajorCampus(majorCampusId));
    }

    @GetMapping("/{majorCampusId}")
    public ResponseEntity<?> getDetailMajorCampus(@PathVariable Long majorCampusId) {
        return Helper.createResponseEntity(majorCampusService.getDetailMajorCampus(majorCampusId));
    }

    @GetMapping("/staff")
    public ResponseEntity<?> findAllStaff() {
        return Helper.createResponseEntity(majorCampusService.getAllStaff());
    }

    @GetMapping("/major")
    public ResponseEntity<?> findAllMajor() {
        return Helper.createResponseEntity(majorCampusService.getAllMajor());
    }

}
