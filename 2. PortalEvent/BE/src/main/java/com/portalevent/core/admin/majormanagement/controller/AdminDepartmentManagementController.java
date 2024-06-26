package com.portalevent.core.admin.majormanagement.controller;

import com.portalevent.core.admin.majormanagement.model.response.AdminCampusResponse;
import com.portalevent.core.admin.majormanagement.model.response.AdminDepartmentResponse;
import com.portalevent.core.admin.majormanagement.service.AdminCampusService;
import com.portalevent.core.admin.majormanagement.service.AdminDepartmentService;
import com.portalevent.infrastructure.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_DEPARTMENT_MANAGEMENT)
@RequiredArgsConstructor
public class AdminDepartmentManagementController {

    private final AdminDepartmentService adminDepartmentService;

    private final AdminCampusService adminCampusService;

    @GetMapping
    public List<AdminDepartmentResponse> getDepartmentList(
            @RequestParam(defaultValue = "", required = false) String value) {
        return adminDepartmentService.getDepartmentList(value);
    }

    @GetMapping("/campus-fetch")
    public List<AdminCampusResponse> getCampusList() {
        return adminCampusService.getCampusList();
    }

}
