package com.portalevent.core.adminho.eventclosedmanagement.controller;

import com.portalevent.core.adminho.eventclosedmanagement.model.request.AdminHOecEventCloseRequest;
import com.portalevent.core.adminho.eventclosedmanagement.service.AdminHOecEventClosedService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_HO_EVENT_CLOSED)

public class AdminHOecEventClosedController {

    private final AdminHOecEventClosedService service;

    public AdminHOecEventClosedController(AdminHOecEventClosedService service) {
        this.service = service;
    }

    @GetMapping
    public PageableObject getAll(final AdminHOecEventCloseRequest request) {
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
