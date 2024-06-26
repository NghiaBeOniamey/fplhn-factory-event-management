package com.portalevent.core.adminho.synchronizedmanagement.controller;

import com.portalevent.core.adminho.semestermanagement.model.request.AdminHOSemesterRequest;
import com.portalevent.core.adminho.synchronizedmanagement.service.AdminHOSynchronizeService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_HO_SYNCHRONIZE_MANAGEMENT)
public class AdminHOSynchronizedController {

    private final AdminHOSynchronizeService adminHOSynchronizeService;

    public AdminHOSynchronizedController(AdminHOSynchronizeService adminHOSynchronizeService) {
        this.adminHOSynchronizeService = adminHOSynchronizeService;
    }

    @PostMapping("/synchronized-campus-identity")
    public void synchronizedCampusIdentity() {
        adminHOSynchronizeService.synchronizeAllIdentity();
    }

    @PostMapping("/synchronized-semester-identity")
    public void synchronizedSemesterIdentity() {
        adminHOSynchronizeService.synchronizeSemestersIdentity();
    }

}
