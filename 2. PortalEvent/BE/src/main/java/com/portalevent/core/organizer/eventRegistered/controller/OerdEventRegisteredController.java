package com.portalevent.core.organizer.eventRegistered.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.eventRegistered.model.request.OerdFindEventRequest;
import com.portalevent.core.organizer.eventRegistered.service.OerdEventRegisteredService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SonPT
 */

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_EVENT_REGISTERED)
public class OerdEventRegisteredController {

    private final OerdEventRegisteredService eventRegisteredService;

    public OerdEventRegisteredController(OerdEventRegisteredService eventRegisteredService) {
        this.eventRegisteredService = eventRegisteredService;
    }

    @GetMapping
    public ResponseObject getAllEventByIdOrganizer(final OerdFindEventRequest request) {
        return new ResponseObject(eventRegisteredService.getAllEventByIdOrganizer(request));
    }

    @GetMapping("/get-all-category")
    public ResponseObject getAll() {
        return new ResponseObject(eventRegisteredService.getAll());
    }

    @GetMapping("/get-all-semester")
    public ResponseObject getAllSemester() {
        return new ResponseObject(eventRegisteredService.getAllSemester());
    }
}
