package com.portalevent.core.organizer.mutipleregister.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.mutipleregister.model.request.OmrFastRegisterRequest;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrEventScheduleResponse;
import com.portalevent.core.organizer.mutipleregister.service.OmrMultipleRegisterService;
import com.portalevent.infrastructure.constant.Constants;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author SonPT
 */

@RestController

@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_MULTIPLE_REGISTER)
public class OmrMultipleRegisterController {

    private final OmrMultipleRegisterService service;

    public OmrMultipleRegisterController(OmrMultipleRegisterService service) {
        this.service = service;
    }

    @GetMapping("/get-all-for-calendar")
    public ResponseObject getAllForCalendar() {
        List<OmrEventScheduleResponse> result = service.getAllForCalendar();
        return new ResponseObject(result);
    }

    @GetMapping("/get-all-info")
    public ResponseObject getAllInfo() {
        return new ResponseObject(service.getAllInfo());
    }

    @PostMapping
    public ResponseObject mutilpleRegister(@RequestBody List<OmrFastRegisterRequest> newEvents) {
        return new ResponseObject(service.multipleRegister(newEvents));
    }

    @GetMapping("/detail-event/{id}")
    public ResponseObject detailEvent(@PathVariable("id") String id) {
        return new ResponseObject(service.getDetail(id));
    }

}
