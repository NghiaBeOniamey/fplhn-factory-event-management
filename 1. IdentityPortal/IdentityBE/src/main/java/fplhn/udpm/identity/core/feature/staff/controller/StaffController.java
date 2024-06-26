package fplhn.udpm.identity.core.feature.staff.controller;

import fplhn.udpm.identity.core.feature.staff.model.request.CreateStaffRequest;
import fplhn.udpm.identity.core.feature.staff.model.request.PaginationStaffRequest;
import fplhn.udpm.identity.core.feature.staff.model.request.UpdateStaffRequest;
import fplhn.udpm.identity.core.feature.staff.service.CourseCampusStaffService;
import fplhn.udpm.identity.core.feature.staff.service.StaffService;
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
@RequiredArgsConstructor
@RequestMapping(ApiConstant.API_STAFF_PREFIX)
@CrossOrigin("*")
@Slf4j
@Hidden
public class StaffController {

    private final StaffService staffService;

    private final CourseCampusStaffService courseCampusStaffService;

    @GetMapping
    public ResponseEntity<?> getAllStaff(PaginationStaffRequest request) {
        return Helper.createResponseEntity(staffService.getAllStaff(request));
    }

    @PostMapping
    public ResponseEntity<?> createStaff(@RequestBody @Valid CreateStaffRequest request) {
        return Helper.createResponseEntity(staffService.createStaff(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody @Valid UpdateStaffRequest request) {
        return Helper.createResponseEntity(staffService.updateStaff(id, request));
    }

    @GetMapping("/department")
    public ResponseEntity<?> getAllDepartmentSelection() {
        return Helper.createResponseEntity(courseCampusStaffService.findAllDepartment());
    }

    @GetMapping("/campus")
    public ResponseEntity<?> getAllCampusSelection() {
        return Helper.createResponseEntity(courseCampusStaffService.findAllCampus());
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        return Helper.createResponseEntity(staffService.updateStaffStatus(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailStaffById(@PathVariable Long id) {
        return Helper.createResponseEntity(staffService.getDetailStaff(id));
    }

}
