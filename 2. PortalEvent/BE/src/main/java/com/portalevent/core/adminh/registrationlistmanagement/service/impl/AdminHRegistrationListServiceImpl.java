package com.portalevent.core.adminh.registrationlistmanagement.service.impl;

import com.portalevent.core.adminh.registrationlistmanagement.model.request.AdminHEventParticipantRequest;
import com.portalevent.core.adminh.registrationlistmanagement.model.response.AdminHEventParticipantResponse;
import com.portalevent.core.adminh.registrationlistmanagement.model.response.AdminHRegistrationResponse;
import com.portalevent.core.adminh.registrationlistmanagement.repository.AdminHRParticipantRepository;
import com.portalevent.core.adminh.registrationlistmanagement.service.AdminHRegistrationListService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHRegistrationListServiceImpl implements AdminHRegistrationListService {

    private final AdminHRParticipantRepository repository;

    private final PortalEventsSession session;

    public AdminHRegistrationListServiceImpl(AdminHRParticipantRepository repository, PortalEventsSession session) {
        this.repository = repository;
        this.session = session;
    }

    //    Hàm lấy ra danh sách người tham gia
    @Override
    public PageableObject<AdminHEventParticipantResponse> getListResgistration(final AdminHEventParticipantRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<AdminHEventParticipantResponse> res = repository.getListResgistration(pageable, req, session.getData());
        return new PageableObject(res);
    }

    @Override
    public List<AdminHRegistrationResponse> getRegistrationList(String idEvent) {
        return repository.getRegistrationList(idEvent);
    }

}
