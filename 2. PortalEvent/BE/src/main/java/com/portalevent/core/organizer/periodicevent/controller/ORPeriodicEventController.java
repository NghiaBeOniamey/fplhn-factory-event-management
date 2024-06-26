package com.portalevent.core.organizer.periodicevent.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.periodicevent.model.request.ORFindPeriodicEventRequest;
import com.portalevent.core.organizer.periodicevent.service.ORPeriodicEventService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author thangncph26123
 */
@RestController
@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_PERIODIC_EVENT)

public class ORPeriodicEventController {

    private final ORPeriodicEventService orPeriodicEventService;

    public ORPeriodicEventController(ORPeriodicEventService orPeriodicEventService) {
        this.orPeriodicEventService = orPeriodicEventService;
    }

    @GetMapping
    public ResponseObject getPage(final ORFindPeriodicEventRequest request) {
        return new ResponseObject(orPeriodicEventService.getPage(request));
    }

    @GetMapping("/list-category")
    public ResponseObject getListCategory() {
        return new ResponseObject(orPeriodicEventService.getAllCategory());
    }

    @GetMapping("/list-object")
    public ResponseObject getListObject() {
        return new ResponseObject(orPeriodicEventService.getAllObject());
    }

    @GetMapping("/list-major")
    public ResponseObject getListMajor() {
        return new ResponseObject(orPeriodicEventService.getAllMajor());
    }

    @GetMapping("/detail")
    public ResponseObject detail(@RequestParam("id") String id) {
        return new ResponseObject(orPeriodicEventService.detail(id));
    }

    @PostMapping("/register-periodic-event")
    public ResponseObject registerPeriodicEvent(@RequestParam("idPeriodicEvent") String idPeriodicEvent) {
        return new ResponseObject(orPeriodicEventService.registerPeriodicEvent(idPeriodicEvent));
    }
}
