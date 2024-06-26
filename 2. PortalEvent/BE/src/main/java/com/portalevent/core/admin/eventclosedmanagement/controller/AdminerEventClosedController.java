package com.portalevent.core.admin.eventclosedmanagement.controller;

import com.portalevent.core.admin.eventclosedmanagement.model.request.AdminerEventCloseRequest;
import com.portalevent.core.admin.eventclosedmanagement.service.AdminerEventClosedService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_EVENT_CLOSED)

public class AdminerEventClosedController {

    private final AdminerEventClosedService service;

    public AdminerEventClosedController(AdminerEventClosedService service) {
        this.service = service;
    }

    @GetMapping
    public PageableObject getAll(final AdminerEventCloseRequest request) {
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
