package com.portalevent.core.adminh.eventapprovedmanagement.controller;

import com.portalevent.core.adminh.eventapprovedmanagement.model.request.AdminHeaEventApprovedRequest;
import com.portalevent.core.adminh.eventapprovedmanagement.service.AdminHeaEventApprovedService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_H_EVENT_APPROVED)
public class AdminHeaEventApprovedController {

    private final AdminHeaEventApprovedService service;

    public AdminHeaEventApprovedController(AdminHeaEventApprovedService service) {
        this.service = service;
    }

    @PostMapping("/list-event-approved")
    public ResponseObject getListEventApproved(@RequestBody AdminHeaEventApprovedRequest request) {
        return new ResponseObject(service.getListEventAppoved(request));
    }

    @GetMapping("/event-category/list")
    public ResponseObject getEventCategory() {
        return new ResponseObject(service.getEventCategory());
    }

}
