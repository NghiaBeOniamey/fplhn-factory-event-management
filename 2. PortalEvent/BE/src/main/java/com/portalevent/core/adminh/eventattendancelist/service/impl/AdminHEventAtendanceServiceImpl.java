package com.portalevent.core.adminh.eventattendancelist.service.impl;

import com.portalevent.core.adminh.eventattendancelist.model.request.AdminHEventAttendanceSearchRequest;
import com.portalevent.core.adminh.eventattendancelist.model.response.AdminHAttendanceResponse;
import com.portalevent.core.adminh.eventattendancelist.model.response.AdminHEventAttendanceListResponse;
import com.portalevent.core.adminh.eventattendancelist.repository.AdminHParticipantRepository;
import com.portalevent.core.adminh.eventattendancelist.service.AdminHEventAttendanceService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHEventAtendanceServiceImpl implements AdminHEventAttendanceService {

    private final AdminHParticipantRepository repository;
    private final PortalEventsSession session;

    public AdminHEventAtendanceServiceImpl(AdminHParticipantRepository repository, PortalEventsSession session) {
        this.repository = repository;
        this.session = session;
    }

    @Override
    public PageableObject<AdminHEventAttendanceListResponse> getAllAttendance(AdminHEventAttendanceSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<AdminHEventAttendanceListResponse> res = repository.getAllAttendance(pageable, request, session.getData());
        return new PageableObject(res);
    }

    @Override
    public List<AdminHAttendanceResponse> getAttendanceList(String idEvent) {
        return repository.getAttendanceList(idEvent);
    }

}
