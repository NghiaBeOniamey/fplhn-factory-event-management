package com.portalevent.core.adminho.statisticseventmanagement.controller;

import com.portalevent.core.adminho.statisticseventmanagement.model.request.AdminHOIdRequest;
import com.portalevent.core.adminho.statisticseventmanagement.model.request.AdminHOSCDRequest;
import com.portalevent.core.adminho.statisticseventmanagement.service.AdminHOseStatisticEventService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HoangDV
 */

@RestController
@RequestMapping(Constants.UrlPath.URL_APO_ADMIN_HO_STATISTICS_EVENT)
@RequiredArgsConstructor
public class AdminHOseStatisticsEventController {

    private final AdminHOseStatisticEventService statisticEventService;

    @GetMapping("/get-all-semester")
    public ResponseObject getAllSemester() {
        return new ResponseObject(statisticEventService.getAllSemester());
    }


    @GetMapping("/get-all-category")
    public ResponseObject getAllCategory() {
        return new ResponseObject(statisticEventService.getAllCategory());
    }

    @GetMapping("/get-all-campus")
    public ResponseObject getAllCampus() {
        return new ResponseObject(statisticEventService.getAllCampus());
    }

    @GetMapping("/get-department/{campusId}")
    public ResponseObject getDepartmentByCampusCode(@PathVariable Long campusId) {
        return new ResponseObject(statisticEventService.getDepartmentByCampusId(campusId));
    }

    // Lấy ra tổng số event (all status)
    @GetMapping("/get-sum-event")
    public ResponseObject getSumEvent(AdminHOSCDRequest request) {
        return new ResponseObject(statisticEventService.getSumEventBySemester(request));
    }

    // Lấy all event in semester
    @GetMapping("/get-all-event")
    public ResponseObject getAllEvent(AdminHOSCDRequest request) {
        return new ResponseObject(statisticEventService.getEventBySemester(request));
    }

    // Lấy ra top 3 event có người tham gia nhiều nhất
    @GetMapping("/get-top-event")
    public ResponseObject getTopEvent(AdminHOSCDRequest request) {
        return new ResponseObject(statisticEventService.getTopEvent(request));
    }

    // Lấy ra top những giảng viên tham ra nhiều sự kiện nhất trong kỳ
    @GetMapping("/get-list-organizer")
    public ResponseObject getListOrganizer(AdminHOSCDRequest request) {
        return new ResponseObject(statisticEventService.getListOrganizer(request));
    }

    // Lấy ra all event in major
    @GetMapping("/get-event-in-major")
    public ResponseObject getListEventInMajor(AdminHOSCDRequest request) {
        return new ResponseObject(statisticEventService.getEventInMajorByIdSemester(request));
    }

    @GetMapping("/get-parcitipant-in-evenet")
    public ResponseObject getListParticipantInEvent(AdminHOSCDRequest request) {
        return new ResponseObject(statisticEventService.getListParticipantInEvent(request));
    }

    @GetMapping("/get-participant-in-event-by-category")
    public ResponseObject getListParticipantInEventByCategory(AdminHOIdRequest request) {
        return new ResponseObject(statisticEventService.getListParticipantInEventByCategory(request));
    }

    @GetMapping("/get-lecturer-in-event")
    public ResponseObject getListLecturerInEvent(AdminHOSCDRequest request) {
        return new ResponseObject(statisticEventService.getListLecturerInEvent(request));
    }

}
