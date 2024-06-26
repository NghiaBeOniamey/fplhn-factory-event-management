package com.portalevent.core.admin.periodiceventmanagement.controller;

import com.portalevent.core.admin.periodiceventmanagement.model.request.AdminerDPDCreatePeriodicEventRequest;
import com.portalevent.core.admin.periodiceventmanagement.model.request.AdminerDPDFindPeriodEventRequest;
import com.portalevent.core.admin.periodiceventmanagement.model.request.AdminerDPDUpdatePeriodicEventRequest;
import com.portalevent.core.admin.periodiceventmanagement.service.AdminerDPDPeriodicEventService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.*;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_PERIODIC_EVENT)

public class AdminerDPDPeriodEventController {

    private final AdminerDPDPeriodicEventService apdPeriodicEventService;

    public AdminerDPDPeriodEventController(AdminerDPDPeriodicEventService apdPeriodicEventService) {
        this.apdPeriodicEventService = apdPeriodicEventService;
    }

    @GetMapping
    public ResponseObject getPage(final AdminerDPDFindPeriodEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.getPage(request));
    }

    @GetMapping("/wait-approver")
    public ResponseObject getPageEventWaitApprover(final AdminerDPDFindPeriodEventRequest request) {
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
    public ResponseObject create(@RequestBody AdminerDPDCreatePeriodicEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.create(request));
    }

    @PutMapping
    public ResponseObject update(@RequestBody AdminerDPDUpdatePeriodicEventRequest request) {
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
