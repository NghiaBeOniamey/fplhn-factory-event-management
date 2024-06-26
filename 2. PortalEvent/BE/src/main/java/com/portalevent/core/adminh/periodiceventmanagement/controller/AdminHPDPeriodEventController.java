package com.portalevent.core.adminh.periodiceventmanagement.controller;

import com.portalevent.core.adminh.periodiceventmanagement.model.request.AdminHPDCreatePeriodicEventRequest;
import com.portalevent.core.adminh.periodiceventmanagement.model.request.AdminHPDFindPeriodEventRequest;
import com.portalevent.core.adminh.periodiceventmanagement.model.request.AdminHPDUpdatePeriodicEventRequest;
import com.portalevent.core.adminh.periodiceventmanagement.service.AdminHPDPeriodicEventService;
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
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_H_PERIODIC_EVENT)

public class AdminHPDPeriodEventController {

    private final AdminHPDPeriodicEventService apdPeriodicEventService;

    public AdminHPDPeriodEventController(AdminHPDPeriodicEventService apdPeriodicEventService) {
        this.apdPeriodicEventService = apdPeriodicEventService;
    }

    @GetMapping
    public ResponseObject getPage(final AdminHPDFindPeriodEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.getPage(request));
    }

    @GetMapping("/wait-approver")
    public ResponseObject getPageEventWaitApprover(final AdminHPDFindPeriodEventRequest request) {
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
    public ResponseObject create(@RequestBody AdminHPDCreatePeriodicEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.create(request));
    }

    @PutMapping
    public ResponseObject update(@RequestBody AdminHPDUpdatePeriodicEventRequest request) {
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
