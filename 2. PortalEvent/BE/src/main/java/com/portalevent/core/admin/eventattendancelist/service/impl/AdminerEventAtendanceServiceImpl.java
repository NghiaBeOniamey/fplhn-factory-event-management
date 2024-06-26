package com.portalevent.core.admin.eventattendancelist.service.impl;

import com.portalevent.core.admin.eventattendancelist.model.request.AdminerEventAttendanceSearchRequest;
import com.portalevent.core.admin.eventattendancelist.model.response.AdminerAttendanceResponse;
import com.portalevent.core.admin.eventattendancelist.model.response.AdminerEventAttendanceListResponse;
import com.portalevent.core.admin.eventattendancelist.repository.AdminerParticipantRepository;
import com.portalevent.core.admin.eventattendancelist.service.AdminerEventAttendanceService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminerEventAtendanceServiceImpl implements AdminerEventAttendanceService {

    private final AdminerParticipantRepository repository;
    private final PortalEventsSession session;

    public AdminerEventAtendanceServiceImpl(AdminerParticipantRepository repository, PortalEventsSession session) {
        this.repository = repository;
        this.session = session;
    }

    @Override
    public PageableObject<AdminerEventAttendanceListResponse> getAllAttendance(AdminerEventAttendanceSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<AdminerEventAttendanceListResponse> res = repository.getAllAttendance(pageable, request, session.getData());
        return new PageableObject(res);
    }

    @Override
    public List<AdminerAttendanceResponse> getAttendanceList(String idEvent) {
        return repository.getAttendanceList(idEvent);
    }

}
