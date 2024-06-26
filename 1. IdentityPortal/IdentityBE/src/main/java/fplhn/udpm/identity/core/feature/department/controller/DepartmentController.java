package fplhn.udpm.identity.core.feature.department.controller;

import fplhn.udpm.identity.core.feature.department.model.request.CreateDepartmentRequest;
import fplhn.udpm.identity.core.feature.department.model.request.DepartmentPaginationRequest;
import fplhn.udpm.identity.core.feature.department.model.request.UpdateDepartmentRequest;
import fplhn.udpm.identity.core.feature.department.service.DepartmentService;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.util.Helper;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequestMapping(ApiConstant.API_DEPARTMENT_PREFIX)
@CrossOrigin("*")
@RequiredArgsConstructor
@Hidden
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public ResponseEntity<?> getAllDepartment(DepartmentPaginationRequest request) {
        return Helper.createResponseEntity(departmentService.getAllDepartmentPagination(request));
    }

    @PostMapping
    public ResponseEntity<?> createDepartment(@Valid @RequestBody CreateDepartmentRequest request) {
        return Helper.createResponseEntity(departmentService.createDepartment(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@Valid @RequestBody UpdateDepartmentRequest request, @PathVariable Long id) {
        return Helper.createResponseEntity(departmentService.updateDepartment(request, id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        return Helper.createResponseEntity(departmentService.deleteDepartment(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> getAllDepartmentSearch() {
        return Helper.createResponseEntity(departmentService.getListDepartment());
    }

}
