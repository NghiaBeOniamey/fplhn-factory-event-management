package com.portalevent.core.admin.periodiceventmanagement.service;

import com.portalevent.core.admin.periodiceventmanagement.model.request.AdminerDPDCreatePeriodicEventRequest;
import com.portalevent.core.admin.periodiceventmanagement.model.request.AdminerDPDFindPeriodEventRequest;
import com.portalevent.core.admin.periodiceventmanagement.model.request.AdminerDPDUpdatePeriodicEventRequest;
import com.portalevent.core.admin.periodiceventmanagement.model.response.AdminerDPDDetailPeriodicEventCustom;
import com.portalevent.core.admin.periodiceventmanagement.model.response.AdminerDPDPeriodicEventResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.PeriodicEvent;
import com.portalevent.infrastructure.projection.SimpleEntityProjection;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdminerDPDPeriodicEventService {

    PageableObject<AdminerDPDPeriodicEventResponse> getPage(final AdminerDPDFindPeriodEventRequest request);

    PageableObject<AdminerDPDPeriodicEventResponse> getPageEventWaitApprover(final AdminerDPDFindPeriodEventRequest request);

    List<SimpleEntityProjection> getAllCategory();

    List<SimpleEntityProjection> getAllObject();

    List<SimpleEntityProjection> getAllMajor();

    PeriodicEvent create(@Valid AdminerDPDCreatePeriodicEventRequest request);

    PeriodicEvent update(@Valid AdminerDPDUpdatePeriodicEventRequest request);

    String delete(String id);

    AdminerDPDDetailPeriodicEventCustom detail(String id);

}
