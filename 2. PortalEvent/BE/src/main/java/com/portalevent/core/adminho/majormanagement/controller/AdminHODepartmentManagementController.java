package com.portalevent.core.adminho.majormanagement.controller;

import com.portalevent.core.adminho.majormanagement.model.response.AdminHOCampusResponse;
import com.portalevent.core.adminho.majormanagement.model.response.AdminHODepartmentResponse;
import com.portalevent.core.adminho.majormanagement.service.AdminHOCampusService;
import com.portalevent.core.adminho.majormanagement.service.AdminHODepartmentService;
import com.portalevent.infrastructure.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_HO_DEPARTMENT_MANAGEMENT)
@RequiredArgsConstructor
public class AdminHODepartmentManagementController {

    private final AdminHODepartmentService adminHODepartmentService;

    private final AdminHOCampusService adminHOCampusService;

    @GetMapping
    public List<AdminHODepartmentResponse> getDepartmentList(
            @RequestParam(defaultValue = "", required = false) String value,
            @RequestParam(defaultValue = "", required = false) String campusCode) {
        return adminHODepartmentService.getDepartmentList(value, campusCode);
    }

    @GetMapping("/campus-fetch")
    public List<AdminHOCampusResponse> getCampusList() {
        return adminHOCampusService.getCampusList();
    }

}
