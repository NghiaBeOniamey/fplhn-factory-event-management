package com.portalevent.core.adminh.eventclosedmanagement.controller;

import com.portalevent.core.adminh.eventclosedmanagement.model.request.AdminHecEventCloseRequest;
import com.portalevent.core.adminh.eventclosedmanagement.service.AdminHecEventClosedService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_H_EVENT_CLOSED)

public class AdminHecEventClosedController {

    private final AdminHecEventClosedService service;

    public AdminHecEventClosedController(AdminHecEventClosedService service) {
        this.service = service;
    }

    @GetMapping
    public PageableObject getAll(final AdminHecEventCloseRequest request) {
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
