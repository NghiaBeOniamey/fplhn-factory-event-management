package com.portalevent.core.approver.statisticsEvent.controller;

import com.portalevent.core.approver.statisticsEvent.service.AseStatisticEventService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.infrastructure.constant.Constants;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HoangDV
 */

@RestController
@RequestMapping(Constants.UrlPath.URL_APO_APPROVER_STATISTICS_EVENT)
public class AseStatisticsEventController {

    private final AseStatisticEventService statisticEventService;

    private final PortalEventsSession session;

    public AseStatisticsEventController(AseStatisticEventService statisticEventService, PortalEventsSession session) {
        this.statisticEventService = statisticEventService;
        this.session = session;
    }

    @GetMapping("/get-all-semester")
    public ResponseObject getAllSemester() {
        return new ResponseObject(statisticEventService.getAllSemester());
    }

    // Lấy ra tổng số event (all status)
    @GetMapping("/get-sum-event/{id}")
    public ResponseObject getSumEvent(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticEventService.getSumEventBySemester(idSemester));
    }

    // Lấy all event in semester
    @GetMapping("/get-all-event/{id}")
    public ResponseObject getAllEvent(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticEventService.getEventBySemester(idSemester));
    }

    // Lấy ra top 3 event có người tham gia nhiều nhất
    @GetMapping("/get-top-event/{id}")
    public ResponseObject getTopEvent(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticEventService.getTopEvent(idSemester));
    }

    // Lấy ra top những giảng viên tham ra nhiều sự kiện nhất trong kỳ
    @GetMapping("/get-list-organizer/{id}")
    public ResponseObject getListOrganizer(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticEventService.getListOrganizer(idSemester));
    }

    // Lấy ra all event in major
    @GetMapping("/get-event-in-major/{id}")
    public ResponseObject getListEventInMajor(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticEventService.getEventInMajorByIdSemester(idSemester, session.getData()));
    }

    @GetMapping("/get-parcitipant-in-evenet/{id}")
    public ResponseObject getListParticipantInEvent(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticEventService.getListParticipantInEvent(idSemester));
    }

    @GetMapping("/get-participant-in-event-by-category")
    public ResponseObject getListParticipantInEventByCategory(@RequestParam("idSemester") String idSemester,
                                                              @RequestParam("idCategory") String idCategory) {
        return new ResponseObject(statisticEventService.getListParticipantInEventByCategory(idSemester, idCategory));
    }

    @GetMapping("/get-lecturer-in-event/{id}")
    public ResponseObject getListLecturerInEvent(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticEventService.getListLecturerInEvent(idSemester));
    }

    @GetMapping("/get-all-category")
    public ResponseObject getAllCategory() {
        return new ResponseObject(statisticEventService.getAllCategory());
    }
}
