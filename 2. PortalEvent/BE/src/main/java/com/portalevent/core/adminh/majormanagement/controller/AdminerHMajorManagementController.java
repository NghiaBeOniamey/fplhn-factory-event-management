package com.portalevent.core.adminh.majormanagement.controller;

import com.portalevent.core.adminh.majormanagement.model.response.AdminerHMajorResponse;
import com.portalevent.core.adminh.majormanagement.service.AdminerHMajorService;
import com.portalevent.infrastructure.constant.Constants;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_H_MAJOR_MANAGEMENT)
public class AdminerHMajorManagementController {

//    private final AdminerHMajorService ammMajorService;

    @Autowired
    private PortalEventsSession portalEventsSession;

//    public AdminerHMajorManagementController(AdminerHMajorService ammMajorService) {
//        this.ammMajorService = ammMajorService;
//    }

//    @GetMapping
//    public List<AdminerHMajorResponse> getMajorList(@RequestParam(defaultValue = "", required = false) String value) {
//        return ammMajorService.getMajorList(value, portalEventsSession.getCurrentTrainingFacilityCode());
//    }
//
//    @GetMapping("/majors-fetch")
//    public void fetchMajors() {
//        ammMajorService.fetchAndSaveDepartments();
//    }

}
