package com.portalevent.core.approver.eventapproved.controller;

import com.portalevent.core.approver.eventapproved.model.request.AeaEventApprovedRequest;
import com.portalevent.core.approver.eventapproved.service.AeaEventApprovedService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_EVENT_APPROVED)

public class AeaEventApprovedController {
    @Autowired
    private AeaEventApprovedService service;

    @PostMapping("/list-event-approved")
    public ResponseObject getListEventApproved(@RequestBody AeaEventApprovedRequest request) {
        return new ResponseObject(service.getListEventAppoved(request));
    }

    @GetMapping("/event-category/list")
    public ResponseObject getEventCategory() {
        return new ResponseObject(service.getEventCategory());
    }
}
