//package com.portalevent.core.organizer.eventInSemester.controller;
//
//import com.portalevent.core.common.PageableObject;
//import com.portalevent.core.organizer.eventInSemester.model.request.OeisEventInSemesterRequest;
//import com.portalevent.core.organizer.eventInSemester.model.response.OeisOrganizerResponse;
//import com.portalevent.core.organizer.eventInSemester.model.response.OeisSemesterResponse;
//import com.portalevent.core.organizer.eventInSemester.service.OeisEventInSemesterService;
//import com.portalevent.infrastructure.constant.Constants;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//
//@RestController
//@RequestMapping(Constants.UrlPath.URL_API_ADMINISTRATIVE_EVENT_IN_SEMESTER)
//public class OeisEventInSemesterController {
//
//    private final OeisEventInSemesterService orEventInSemesterService;
//
//    public OeisEventInSemesterController(OeisEventInSemesterService orEventInSemesterService) {
//        this.orEventInSemesterService = orEventInSemesterService;
//    }
//
//    @GetMapping
//    public PageableObject getAll(final OeisEventInSemesterRequest request) {
//        return orEventInSemesterService.getAll(request);
//    }
//
//    @GetMapping("/all-semester")
//    public List<OeisSemesterResponse> getAllSemester() {
//        return orEventInSemesterService.getAllSemester();
//    }
//
//    @GetMapping("/all-organizer")
//    public List<OeisOrganizerResponse> getAllOrganizer() {
//        return orEventInSemesterService.getAllOrganizer();
//    }
//}
