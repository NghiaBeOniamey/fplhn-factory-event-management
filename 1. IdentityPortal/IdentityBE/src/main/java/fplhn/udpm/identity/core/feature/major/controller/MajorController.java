package fplhn.udpm.identity.core.feature.major.controller;

import fplhn.udpm.identity.core.feature.major.model.request.CreateMajorRequest;
import fplhn.udpm.identity.core.feature.major.model.request.MajorRequest;
import fplhn.udpm.identity.core.feature.major.model.request.UpdateMajorRequest;
import fplhn.udpm.identity.core.feature.major.service.MajorService;
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
@RequestMapping(ApiConstant.API_MAJOR_PREFIX)
@CrossOrigin("*")
@Slf4j
@Hidden
public class MajorController {

    private final MajorService majorService;

    @GetMapping
    public ResponseEntity<?> findAllMajor(MajorRequest request) {
        return Helper.createResponseEntity(majorService.findAllMajor(request));
    }

    @PostMapping
    public ResponseEntity<?> addMajor(@RequestBody CreateMajorRequest request) {
        return Helper.createResponseEntity(majorService.addMajor(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMajor(@PathVariable Long id, @RequestBody UpdateMajorRequest request) {
        return Helper.createResponseEntity(majorService.updateMajor(request, id));
    }

    @PutMapping("/status/{majorId}")
    public ResponseEntity<?> changeStatus(@PathVariable Long majorId) {
        return Helper.createResponseEntity(majorService.changeStatus(majorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findDetailMajor(@PathVariable Long id) {
        return Helper.createResponseEntity(majorService.findDetailMajor(id));
    }

}
