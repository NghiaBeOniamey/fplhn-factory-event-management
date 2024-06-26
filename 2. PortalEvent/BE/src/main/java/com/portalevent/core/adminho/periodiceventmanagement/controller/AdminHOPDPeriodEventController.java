package com.portalevent.core.adminho.periodiceventmanagement.controller;

import com.portalevent.core.adminho.periodiceventmanagement.model.request.AdminHOPDCreatePeriodicEventRequest;
import com.portalevent.core.adminho.periodiceventmanagement.model.request.AdminHOPDFindPeriodEventRequest;
import com.portalevent.core.adminho.periodiceventmanagement.model.request.AdminHOPDUpdatePeriodicEventRequest;
import com.portalevent.core.adminho.periodiceventmanagement.service.AdminHOPDPeriodicEventService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_HO_PERIODIC_EVENT)

public class AdminHOPDPeriodEventController {

    private final AdminHOPDPeriodicEventService apdPeriodicEventService;

    public AdminHOPDPeriodEventController(AdminHOPDPeriodicEventService apdPeriodicEventService) {
        this.apdPeriodicEventService = apdPeriodicEventService;
    }

    @GetMapping
    public ResponseObject getPage(final AdminHOPDFindPeriodEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.getPage(request));
    }

    @GetMapping("/wait-approver")
    public ResponseObject getPageEventWaitApprover(final AdminHOPDFindPeriodEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.getPageEventWaitApprover(request));
    }

    @GetMapping("/list-category")
    public ResponseObject getListCategory() {
        return new ResponseObject(apdPeriodicEventService.getAllCategory());
    }

    @GetMapping("/list-object")
    public ResponseObject getListObject() {
        return new ResponseObject(apdPeriodicEventService.getAllObject());
    }

    @GetMapping("/list-major")
    public ResponseObject getListMajor() {
        return new ResponseObject(apdPeriodicEventService.getAllMajor());
    }

    @PostMapping
    public ResponseObject create(@RequestBody AdminHOPDCreatePeriodicEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.create(request));
    }

    @PutMapping
    public ResponseObject update(@RequestBody AdminHOPDUpdatePeriodicEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.update(request));
    }

    @DeleteMapping
    public ResponseObject delete(@RequestParam("id") String id) {
        return new ResponseObject(apdPeriodicEventService.delete(id));
    }

    @GetMapping("/detail")
    public ResponseObject detail(@RequestParam("id") String id) {
        return new ResponseObject(apdPeriodicEventService.detail(id));
    }
}
