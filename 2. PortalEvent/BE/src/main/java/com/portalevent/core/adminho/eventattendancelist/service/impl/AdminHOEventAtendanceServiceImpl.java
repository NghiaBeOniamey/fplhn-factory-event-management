package com.portalevent.core.adminho.eventattendancelist.service.impl;

import com.portalevent.core.adminho.eventattendancelist.model.request.AdminHOEventAttendanceSearchRequest;
import com.portalevent.core.adminho.eventattendancelist.model.response.AdminHOAttendanceResponse;
import com.portalevent.core.adminho.eventattendancelist.model.response.AdminHOEventAttendanceListResponse;
import com.portalevent.core.adminho.eventattendancelist.repository.AdminHOParticipantRepository;
import com.portalevent.core.adminho.eventattendancelist.service.AdminHOEventAttendanceService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHOEventAtendanceServiceImpl implements AdminHOEventAttendanceService {

    private final AdminHOParticipantRepository repository;
    private final PortalEventsSession session;

    public AdminHOEventAtendanceServiceImpl(AdminHOParticipantRepository repository, PortalEventsSession session) {
        this.repository = repository;
        this.session = session;
    }

    @Override
    public PageableObject<AdminHOEventAttendanceListResponse> getAllAttendance(AdminHOEventAttendanceSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<AdminHOEventAttendanceListResponse> res = repository.getAllAttendance(pageable, request, session.getData());
        return new PageableObject(res);
    }

    @Override
    public List<AdminHOAttendanceResponse> getAttendanceList(String idEvent) {
        return repository.getAttendanceList(idEvent);
    }

}
