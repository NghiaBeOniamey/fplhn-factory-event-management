package com.portalevent.core.admin.eventapprovedmanagement.controller;

import com.portalevent.core.admin.eventapprovedmanagement.model.request.AdminerEventApprovedRequest;
import com.portalevent.core.admin.eventapprovedmanagement.service.AdminerEventApprovedService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_EVENT_APPROVED)
public class AdminerEventApprovedController {

    private final AdminerEventApprovedService service;

    public AdminerEventApprovedController(AdminerEventApprovedService service) {
        this.service = service;
    }

    @PostMapping("/list-event-approved")
    public ResponseObject getListEventApproved(@RequestBody AdminerEventApprovedRequest request) {
        return new ResponseObject(service.getListEventAppoved(request));
    }

    @GetMapping("/event-category/list")
    public ResponseObject getEventCategory() {
        return new ResponseObject(service.getEventCategory());
    }

}
