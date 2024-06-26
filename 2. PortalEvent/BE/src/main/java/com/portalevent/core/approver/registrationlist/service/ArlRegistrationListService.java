package com.portalevent.core.approver.registrationlist.service;

import com.portalevent.core.approver.registrationlist.model.request.ArlEventParticipantRequest;
import com.portalevent.core.approver.registrationlist.model.response.ArlEventParticipantResponse;
import com.portalevent.core.approver.registrationlist.model.response.ArlRegistrationResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.Event;

import java.util.List;

public interface ArlRegistrationListService {

    Event detail(String id);


    PageableObject<ArlEventParticipantResponse> getListResgistration(final ArlEventParticipantRequest req);


    Integer countAllSearchQuestion(final ArlEventParticipantRequest req);

    List<ArlRegistrationResponse> getRegistrationList(String idEvent);

}
