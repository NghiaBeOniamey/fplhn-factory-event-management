package fplhn.udpm.identity.core.feature.semester.controller;

import fplhn.udpm.identity.core.feature.semester.model.request.ModifySemesterRequest;
import fplhn.udpm.identity.core.feature.semester.model.request.SemesterPaginationRequest;
import fplhn.udpm.identity.core.feature.semester.service.SemesterService;
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
@RequestMapping(ApiConstant.API_SEMESTER_PREFIX)
@CrossOrigin("*")
@Slf4j
@Hidden
public class SemesterController {

    private final SemesterService semesterService;

    @GetMapping
    public ResponseEntity<?> findAllSemester(SemesterPaginationRequest request) {
        return Helper.createResponseEntity(semesterService.getAllSemester(request));
    }

    @PostMapping
    public ResponseEntity<?> addSemester(@RequestBody ModifySemesterRequest request) {
        return Helper.createResponseEntity(semesterService.createSemester(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSemester(@PathVariable Long id, @RequestBody ModifySemesterRequest request) {
        return Helper.createResponseEntity(semesterService.updateSemester(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findSemesterById(@PathVariable Long id) {
        return Helper.createResponseEntity(semesterService.getDetailSemester(id));
    }

}
