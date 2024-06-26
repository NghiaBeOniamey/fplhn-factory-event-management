package com.portalevent.core.approver.eventattendancelist.service.impl;

import com.portalevent.core.approver.eventattendancelist.model.request.AealEventAttendanceSearchRequest;
import com.portalevent.core.approver.eventattendancelist.model.response.AealEventAttendanceListResponse;
import com.portalevent.core.approver.eventattendancelist.model.response.ArlAttendanceResponse;
import com.portalevent.core.approver.eventattendancelist.repository.AealEventAttendanceRepository;
import com.portalevent.core.approver.eventattendancelist.service.AealEventAttendanceService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AealEventAtendanceServiceImpl implements AealEventAttendanceService {

    private final AealEventAttendanceRepository repository;
    private final PortalEventsSession session;

    public AealEventAtendanceServiceImpl(AealEventAttendanceRepository repository, PortalEventsSession session) {
        this.repository = repository;
        this.session = session;
    }

    @Override
    public PageableObject<AealEventAttendanceListResponse> getAllAttendance(AealEventAttendanceSearchRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        Page<AealEventAttendanceListResponse> res = repository.getAllAttendance(pageable, request, session.getData());
        return new PageableObject(res);
    }

    @Override
    public List<ArlAttendanceResponse> getAttendanceList(String idEvent) {
        return repository.getAttendanceList(idEvent);
    }

    @Override
    public List<AealEventAttendanceListResponse> getAttendanceListByIdEvent(String idEvent) {
        return repository.getAttendanceListByIdEvent(idEvent);
    }
}
