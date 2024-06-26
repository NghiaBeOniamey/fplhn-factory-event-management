package com.portalevent.core.adminho.eventapprovedmanagement.controller;

import com.portalevent.core.adminho.eventapprovedmanagement.model.request.AdminHOeaEventApprovedRequest;
import com.portalevent.core.adminho.eventapprovedmanagement.service.AdminHOeaEventApprovedService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_HO_EVENT_APPROVED)
public class AdminHOeaEventApprovedController {

    private final AdminHOeaEventApprovedService service;

    public AdminHOeaEventApprovedController(AdminHOeaEventApprovedService service) {
        this.service = service;
    }

    @PostMapping("/list-event-approved")
    public ResponseObject getListEventApproved(@RequestBody AdminHOeaEventApprovedRequest request) {
        return new ResponseObject(service.getListEventAppoved(request));
    }

    @GetMapping("/event-category/list")
    public ResponseObject getEventCategory() {
        return new ResponseObject(service.getEventCategory());
    }

}
