package com.portalevent.core.admin.registrationlistmanagement.service;

import com.portalevent.core.admin.registrationlistmanagement.model.request.AdminerEventParticipantRequest;
import com.portalevent.core.admin.registrationlistmanagement.model.response.AdminerEventParticipantResponse;
import com.portalevent.core.admin.registrationlistmanagement.model.response.AdminerRegistrationResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminerRegistrationListService {

    PageableObject<AdminerEventParticipantResponse> getListResgistration(final AdminerEventParticipantRequest req);

    List<AdminerRegistrationResponse> getRegistrationList(String idEvent);

}
