package com.portalevent.core.organizer.statisticsEvent.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.statisticsEvent.service.OseStatisticsEventService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author HoangDV
 */

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_STATISTIC_EVENT)
public class OseStatisticsEventController {
    @Autowired
    private OseStatisticsEventService statisticsEventService;

    @GetMapping("/get-all-semester")
    public ResponseObject getAllSemester() {
        return new ResponseObject(statisticsEventService.getAllSemester());
    }

    // Lấy ra tổng các event đã tổ chức kèm role
    @GetMapping("/get-total-role/{id}")
    public ResponseObject getTotalRole(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticsEventService.getRoleUserInEventInSemester(idSemester));
    }

    // Lấy ra top 3 event có người tham gia nhiều nhất
    @GetMapping("/get-top-event/{id}")
    public ResponseObject getTopEvent(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticsEventService.getTopEvent(idSemester));
    }

    // Lấy all event thuộc user and semester
    @GetMapping("/get-all-event/{id}")
    public ResponseObject getAllEvent(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticsEventService.getEventBySemesterAndOrganizer(idSemester));
    }

    // Lấy ra tổng số event của user (all status)
    @GetMapping("/get-sum-event/{id}")
    public ResponseObject  getSumEvent(@PathVariable("id") String idSemester) {
        return new ResponseObject(statisticsEventService.getSumEventBySemesterAndOrganizer(idSemester));
    }

}
