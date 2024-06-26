package com.portalevent.core.admin.statisticseventmanagement.controller;

import com.portalevent.core.admin.statisticseventmanagement.model.request.AdminerIdRequest;
import com.portalevent.core.admin.statisticseventmanagement.model.request.AdminerSemesterMajorRequest;
import com.portalevent.core.admin.statisticseventmanagement.service.AdminerseStatisticEventService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HoangDV
 */

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_STATISTICS_EVENT)
public class AdminerStatisticsEventController {

    private final AdminerseStatisticEventService statisticEventService;

    public AdminerStatisticsEventController(AdminerseStatisticEventService statisticEventService) {
        this.statisticEventService = statisticEventService;
    }

    @GetMapping("/get-all-semester")
    public ResponseObject getAllSemester() {
        return new ResponseObject(statisticEventService.getAllSemester());
    }


    @GetMapping("/get-all-category")
    public ResponseObject getAllCategory() {
        return new ResponseObject(statisticEventService.getAllCategory());
    }

    @GetMapping("/get-all-department")
    public ResponseObject getAllDepartment() {
        return new ResponseObject(statisticEventService.getAllDepartment());
    }

    // Lấy ra tổng số event (all status)
    @GetMapping("/get-sum-event")
    public ResponseObject getSumEvent(AdminerSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getSumEventBySemester(request));
    }

    // Lấy all event in semester
    @GetMapping("/get-all-event")
    public ResponseObject getAllEvent(AdminerSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getEventBySemester(request));
    }

    // Lấy ra top 3 event có người tham gia nhiều nhất
    @GetMapping("/get-top-event")
    public ResponseObject getTopEvent( AdminerSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getTopEvent(request));
    }

    // Lấy ra top những giảng viên tham ra nhiều sự kiện nhất trong kỳ
    @GetMapping("/get-list-organizer")
    public ResponseObject getListOrganizer(AdminerSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getListOrganizer(request));
    }

    // Lấy ra all event in major
    @GetMapping("/get-event-in-major")
    public ResponseObject getListEventInMajor(AdminerSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getEventInMajorByIdSemester(request));
    }

    @GetMapping("/get-parcitipant-in-evenet")
    public ResponseObject getListParticipantInEvent(AdminerSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getListParticipantInEvent(request));
    }

    @GetMapping("/get-participant-in-event-by-category")
    public ResponseObject getListParticipantInEventByCategory(AdminerIdRequest request) {
        return new ResponseObject(statisticEventService.getListParticipantInEventByCategory(request));
    }

    @GetMapping("/get-lecturer-in-event")
    public ResponseObject getListLecturerInEvent(AdminerSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getListLecturerInEvent(request));
    }

}
