package com.portalevent.core.adminh.statisticseventmanagement.controller;

import com.portalevent.core.adminh.statisticseventmanagement.model.request.AdminHIdRequest;
import com.portalevent.core.adminh.statisticseventmanagement.model.request.AdminHSemesterMajorRequest;
import com.portalevent.core.adminh.statisticseventmanagement.service.AdminHseStatisticEventService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HoangDV
 */

@RestController
@RequestMapping(Constants.UrlPath.URL_APO_ADMIN_H_STATISTICS_EVENT)
public class AdminHseStatisticsEventController {

    private final AdminHseStatisticEventService statisticEventService;

    public AdminHseStatisticsEventController(AdminHseStatisticEventService statisticEventService) {
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
    public ResponseObject getSumEvent(AdminHSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getSumEventBySemester(request));
    }

    // Lấy all event in semester
    @GetMapping("/get-all-event")
    public ResponseObject getAllEvent(AdminHSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getEventBySemester(request));
    }

    // Lấy ra top 3 event có người tham gia nhiều nhất
    @GetMapping("/get-top-event")
    public ResponseObject getTopEvent( AdminHSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getTopEvent(request));
    }

    // Lấy ra top những giảng viên tham ra nhiều sự kiện nhất trong kỳ
    @GetMapping("/get-list-organizer")
    public ResponseObject getListOrganizer(AdminHSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getListOrganizer(request));
    }

    // Lấy ra all event in major
    @GetMapping("/get-event-in-major")
    public ResponseObject getListEventInMajor(AdminHSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getEventInMajorByIdSemester(request));
    }

    @GetMapping("/get-parcitipant-in-evenet")
    public ResponseObject getListParticipantInEvent(AdminHSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getListParticipantInEvent(request));
    }

    @GetMapping("/get-participant-in-event-by-category")
    public ResponseObject getListParticipantInEventByCategory(AdminHIdRequest request) {
        return new ResponseObject(statisticEventService.getListParticipantInEventByCategory(request));
    }

    @GetMapping("/get-lecturer-in-event")
    public ResponseObject getListLecturerInEvent(AdminHSemesterMajorRequest request) {
        return new ResponseObject(statisticEventService.getListLecturerInEvent(request));
    }

}
