package com.portalevent.core.adminho.registrationlistmanagement.service.impl;

import com.portalevent.core.adminho.registrationlistmanagement.model.request.AdminHOEventParticipantRequest;
import com.portalevent.core.adminho.registrationlistmanagement.model.response.AdminHOEventParticipantResponse;
import com.portalevent.core.adminho.registrationlistmanagement.model.response.AdminHORegistrationResponse;
import com.portalevent.core.adminho.registrationlistmanagement.repository.AdminHORParticipantRepository;
import com.portalevent.core.adminho.registrationlistmanagement.service.AdminHORegistrationListService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHORegistrationListServiceImpl implements AdminHORegistrationListService {

    private final AdminHORParticipantRepository repository;

    private final PortalEventsSession session;

    public AdminHORegistrationListServiceImpl(AdminHORParticipantRepository repository, PortalEventsSession session) {
        this.repository = repository;
        this.session = session;
    }

    //    Hàm lấy ra danh sách người tham gia
    @Override
    public PageableObject<AdminHOEventParticipantResponse> getListResgistration(final AdminHOEventParticipantRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<AdminHOEventParticipantResponse> res = repository.getListResgistration(pageable, req, session.getData());
        return new PageableObject(res);
    }

    @Override
    public List<AdminHORegistrationResponse> getRegistrationList(String idEvent) {
        return repository.getRegistrationList(idEvent);
    }

}
