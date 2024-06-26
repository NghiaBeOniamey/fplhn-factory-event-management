package com.portalevent.core.approver.registrationlist.service.impl;

import com.portalevent.core.approver.registrationlist.model.request.ArlEventParticipantRequest;
import com.portalevent.core.approver.registrationlist.model.response.ArlEventParticipantResponse;
import com.portalevent.core.approver.registrationlist.model.response.ArlRegistrationResponse;
import com.portalevent.core.approver.registrationlist.repository.ArlEventRepository;
import com.portalevent.core.approver.registrationlist.repository.ArlParticipantRepository;
import com.portalevent.core.approver.registrationlist.service.ArlRegistrationListService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.registrationList.model.response.OrlQuestionResponse;
import com.portalevent.entity.Event;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArlRegistrationListServiceImpl implements ArlRegistrationListService {
    private final ArlParticipantRepository participantRepository;

    private final ArlEventRepository eventRepository;

    private final PortalEventsSession session;

    public ArlRegistrationListServiceImpl(ArlParticipantRepository participantRepository, ArlEventRepository eventRepository, PortalEventsSession session) {
        this.participantRepository = participantRepository;
        this.eventRepository = eventRepository;
        this.session = session;
    }


    @Override
    public Event detail(String id) {
        return eventRepository.findById(id).get();
    }

    @Override
    public PageableObject<ArlEventParticipantResponse> getListResgistration(final ArlEventParticipantRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<OrlQuestionResponse> res = participantRepository.getListResgistration(pageable, req, session.getData());
        return new PageableObject(res);
    }

    @Override
    public Integer countAllSearchQuestion(final ArlEventParticipantRequest req) {
        return participantRepository.countAllSearchQuestion(req, session.getData());
    }

    @Override
    public List<ArlRegistrationResponse> getRegistrationList(String idEvent) {
        return participantRepository.getRegistrationList(idEvent);
    }

}
