package com.portalevent.core.adminho.registrationlistmanagement.service;

import com.portalevent.core.adminho.registrationlistmanagement.model.request.AdminHOEventParticipantRequest;
import com.portalevent.core.adminho.registrationlistmanagement.model.response.AdminHOEventParticipantResponse;
import com.portalevent.core.adminho.registrationlistmanagement.model.response.AdminHORegistrationResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminHORegistrationListService {

    PageableObject<AdminHOEventParticipantResponse> getListResgistration(final AdminHOEventParticipantRequest req);

    List<AdminHORegistrationResponse> getRegistrationList(String idEvent);

}
