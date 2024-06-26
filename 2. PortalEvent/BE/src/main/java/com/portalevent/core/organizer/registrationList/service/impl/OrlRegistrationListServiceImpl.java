package com.portalevent.core.organizer.registrationList.service.impl;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.registrationList.model.request.OrlFindQuestionsRequest;
import com.portalevent.core.organizer.registrationList.model.response.OrlQuestionResponse;
import com.portalevent.core.organizer.registrationList.model.response.OrlRegistrationResponse;
import com.portalevent.core.organizer.registrationList.repository.OrlEventRepository;
import com.portalevent.core.organizer.registrationList.repository.OrlParticipantRepository;
import com.portalevent.core.organizer.registrationList.service.OrlRegistrationListService;
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
public class OrlRegistrationListServiceImpl implements OrlRegistrationListService {

    private final OrlEventRepository eventRepository;

    private final OrlParticipantRepository participantRepository;

    private final PortalEventsSession session;

    public OrlRegistrationListServiceImpl(OrlEventRepository eventRepository, OrlParticipantRepository participantRepository, PortalEventsSession session) {
        this.eventRepository = eventRepository;
        this.participantRepository = participantRepository;
        this.session = session;
    }

    @Override
    public Event detail(String id) {
        return eventRepository.findById(id).get();
    }

    @Override
    public PageableObject<OrlQuestionResponse> getListResgistration(final OrlFindQuestionsRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<OrlQuestionResponse> res = participantRepository.getListResgistration(pageable, req, session.getData());
        return new PageableObject(res);
    }

    @Override
    public Integer countAllSearchQuestion(final OrlFindQuestionsRequest req) {
        return participantRepository.countAllSearchQuestion(req, session.getData());
    }

    @Override
    public List<OrlRegistrationResponse> getRegistrationList(String idEvent) {
        return participantRepository.getRegistrationList(idEvent);
    }


}
