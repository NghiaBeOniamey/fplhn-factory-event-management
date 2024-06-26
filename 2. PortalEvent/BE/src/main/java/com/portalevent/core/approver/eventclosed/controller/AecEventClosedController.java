package com.portalevent.core.approver.eventclosed.controller;

import com.portalevent.core.approver.eventclosed.model.request.AecEventCloseRequest;
import com.portalevent.core.approver.eventclosed.service.AecEventClosedService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_EVENT_CLOSED)

public class AecEventClosedController {
    @Autowired
    private AecEventClosedService service;

    @GetMapping
    public PageableObject getAll(final AecEventCloseRequest request) {
        return service.getAllEventClose(request);
    }

    @GetMapping("/major-list")
    public ResponseObject getAllMajor() {
        return new ResponseObject(service.getAllMajor());
    }

    @GetMapping("/object-list")
    public ResponseObject getAllObject() {
        return new ResponseObject(service.getAllObject());
    }

    @GetMapping("/category-list")
    public ResponseObject getAllCategory() {
        return new ResponseObject(service.getAllCategory());
    }

    @GetMapping("/semester-list")
    public ResponseObject getAllSemester() {
        return new ResponseObject(service.getAllSemester());
    }
}
