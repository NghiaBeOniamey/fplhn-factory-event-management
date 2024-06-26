package com.portalevent.core.admin.registrationlistmanagement.service.impl;

import com.portalevent.core.admin.registrationlistmanagement.model.request.AdminerEventParticipantRequest;
import com.portalevent.core.admin.registrationlistmanagement.model.response.AdminerEventParticipantResponse;
import com.portalevent.core.admin.registrationlistmanagement.model.response.AdminerRegistrationResponse;
import com.portalevent.core.admin.registrationlistmanagement.repository.AdminerRParticipantRepository;
import com.portalevent.core.admin.registrationlistmanagement.service.AdminerRegistrationListService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.registrationList.model.request.OrlFindQuestionsRequest;
import com.portalevent.core.organizer.registrationList.model.response.OrlQuestionResponse;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminerRegistrationListServiceImpl implements AdminerRegistrationListService {

    private final AdminerRParticipantRepository repository;

    private final PortalEventsSession session;

    public AdminerRegistrationListServiceImpl(AdminerRParticipantRepository repository, PortalEventsSession session) {
        this.repository = repository;
        this.session = session;
    }

    //    Hàm lấy ra danh sách người tham gia
    @Override
    public PageableObject<AdminerEventParticipantResponse> getListResgistration(final AdminerEventParticipantRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<AdminerEventParticipantResponse> res = repository.getListResgistration(pageable, req, session.getData());
        return new PageableObject(res);
    }

    @Override
    public List<AdminerRegistrationResponse> getRegistrationList(String idEvent) {
        return repository.getRegistrationList(idEvent);
    }

}
