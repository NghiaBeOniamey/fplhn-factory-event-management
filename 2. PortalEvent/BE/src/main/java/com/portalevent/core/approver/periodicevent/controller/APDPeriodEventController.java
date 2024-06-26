package com.portalevent.core.approver.periodicevent.controller;

import com.portalevent.core.approver.periodicevent.model.request.APDCreatePeriodicEventRequest;
import com.portalevent.core.approver.periodicevent.model.request.APDFindPeriodEventRequest;
import com.portalevent.core.approver.periodicevent.model.request.APDUpdatePeriodicEventRequest;
import com.portalevent.core.approver.periodicevent.service.APDPeriodicEventService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_PERIODIC_EVENT)

public class APDPeriodEventController {

    @Autowired
    private APDPeriodicEventService apdPeriodicEventService;

    @GetMapping
    public ResponseObject getPage(final APDFindPeriodEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.getPage(request));
    }

    @GetMapping("/wait-approver")
    public ResponseObject getPageEventWaitApprover(final APDFindPeriodEventRequest request) {
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
    public ResponseObject create(@RequestBody APDCreatePeriodicEventRequest request) {
        return new ResponseObject(apdPeriodicEventService.create(request));
    }

    @PutMapping
    public ResponseObject update(@RequestBody APDUpdatePeriodicEventRequest request) {
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
