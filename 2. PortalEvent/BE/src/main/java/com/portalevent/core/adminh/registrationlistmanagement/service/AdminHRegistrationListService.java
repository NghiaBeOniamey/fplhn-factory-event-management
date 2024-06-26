package com.portalevent.core.adminh.registrationlistmanagement.service;

import com.portalevent.core.adminh.registrationlistmanagement.model.request.AdminHEventParticipantRequest;
import com.portalevent.core.adminh.registrationlistmanagement.model.response.AdminHEventParticipantResponse;
import com.portalevent.core.adminh.registrationlistmanagement.model.response.AdminHRegistrationResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminHRegistrationListService {

    PageableObject<AdminHEventParticipantResponse> getListResgistration(final AdminHEventParticipantRequest req);

    List<AdminHRegistrationResponse> getRegistrationList(String idEvent);

}
