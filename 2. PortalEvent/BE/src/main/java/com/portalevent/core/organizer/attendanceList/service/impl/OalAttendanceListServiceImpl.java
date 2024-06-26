package com.portalevent.core.organizer.attendanceList.service.impl;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.attendanceList.model.request.OalFindAttendanceRequest;
import com.portalevent.core.organizer.attendanceList.model.response.OaAttendanceResponse;
import com.portalevent.core.organizer.attendanceList.model.response.OalAttendanceResponse;
import com.portalevent.core.organizer.attendanceList.repository.OalEventRepository;
import com.portalevent.core.organizer.attendanceList.repository.OalParticipantRepository;
import com.portalevent.core.organizer.attendanceList.service.OalAttendanceListService;
import com.portalevent.entity.Event;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author SonPT
 */

@Service
public class OalAttendanceListServiceImpl implements OalAttendanceListService {


    private final OalEventRepository eventRepository;

    private final OalParticipantRepository participantRepository;

    private final PortalEventsSession session;

    public OalAttendanceListServiceImpl(OalEventRepository eventRepository, OalParticipantRepository participantRepository, PortalEventsSession session) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
        this.session = session;
    }

    @Override
    public Event detail(String id) {
        return eventRepository.findById(id).get();
    }

    /***
     *
     * @param request
     * @return Danh sách Điểm danh
     */
    @Override
    public PageableObject<OalAttendanceResponse> getAllAttendance(OalFindAttendanceRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<OalAttendanceResponse> res = participantRepository.getAllAttendance(pageable, request, session.getData());
        return new PageableObject(res);
    }

//    private static void replaceAll(OalFindAttendanceRequest request) {
//        String regexTrim = "\\s+";
//        request.setClassName(request.getClassName().replaceAll(regexTrim, " "));
//        request.setFeedback(request.getFeedback().replaceAll(regexTrim, " "));
//    }

    @Override
    public Integer countAllSearch(final OalFindAttendanceRequest req) {
        return participantRepository.countAllSearch(req, session.getData());
    }

    @Override
    public List<OaAttendanceResponse> getAttendanceList(String idEvent) {
        return participantRepository.getAttendanceList(idEvent);
    }

}
