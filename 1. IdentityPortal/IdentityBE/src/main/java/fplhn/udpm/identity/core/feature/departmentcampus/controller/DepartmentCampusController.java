package fplhn.udpm.identity.core.feature.departmentcampus.controller;

import fplhn.udpm.identity.core.feature.departmentcampus.model.request.DepartmentCampusDetailRequest;
import fplhn.udpm.identity.core.feature.departmentcampus.model.request.ModifyDepartmentCampusRequest;
import fplhn.udpm.identity.core.feature.departmentcampus.service.DepartmentCampusService;
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
@RequestMapping(ApiConstant.API_DEPARTMENT_CAMPUS_PREFIX)
@CrossOrigin("*")
@RequiredArgsConstructor
@Hidden
public class DepartmentCampusController {

    private final DepartmentCampusService departmentCampusService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getAllDepartmentCampusApi(@PathVariable Long id, DepartmentCampusDetailRequest request) {
        return Helper.createResponseEntity(departmentCampusService.getAllDepartmentCampus(id, request));
    }

    @GetMapping("/department-name/{id}")
    public ResponseEntity<?> getAllDepartmentCampusApi(@PathVariable Long id) {
        return Helper.createResponseEntity(departmentCampusService.getDepartmentName(id));
    }

    @PostMapping
    public ResponseEntity<?> createDepartmentCampus(@Valid @RequestBody ModifyDepartmentCampusRequest request) {
        return Helper.createResponseEntity(departmentCampusService.createDepartmentCampus(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartmentCampus(@PathVariable Long id, @Valid @RequestBody ModifyDepartmentCampusRequest request) {
        return Helper.createResponseEntity(departmentCampusService.updateDepartmentCampus(request, id));
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateDepartmentCampus(@PathVariable Long id) {
        return Helper.createResponseEntity(departmentCampusService.deleteDepartmentCampus(id));
    }

    @GetMapping("/campus")
    public ResponseEntity<?> getListTenCoSo() {
        return Helper.createResponseEntity(departmentCampusService.getListCampus());
    }

    @GetMapping("/head-department-campus")
    public ResponseEntity<?> getListHeadDepartmentCampus() {
        return Helper.createResponseEntity(departmentCampusService.getListHeadDepartmentCampus());
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> getListDepartmentCampus(@PathVariable Long id) {
        return Helper.createResponseEntity(departmentCampusService.getListDepartmentCampus(id));
    }

}
