package com.portalevent.core.adminho.periodiceventmanagement.service;

import com.portalevent.core.adminho.periodiceventmanagement.model.request.AdminHOPDCreatePeriodicEventRequest;
import com.portalevent.core.adminho.periodiceventmanagement.model.request.AdminHOPDFindPeriodEventRequest;
import com.portalevent.core.adminho.periodiceventmanagement.model.request.AdminHOPDUpdatePeriodicEventRequest;
import com.portalevent.core.adminho.periodiceventmanagement.model.response.AdminHOPDDetailPeriodicEventCustom;
import com.portalevent.core.adminho.periodiceventmanagement.model.response.AdminHOPDPeriodicEventResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.PeriodicEvent;
import com.portalevent.infrastructure.projection.SimpleEntityProjection;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdminHOPDPeriodicEventService {

    PageableObject<AdminHOPDPeriodicEventResponse> getPage(final AdminHOPDFindPeriodEventRequest request);

    PageableObject<AdminHOPDPeriodicEventResponse> getPageEventWaitApprover(final AdminHOPDFindPeriodEventRequest request);

    List<SimpleEntityProjection> getAllCategory();

    List<SimpleEntityProjection> getAllObject();

    List<SimpleEntityProjection> getAllMajor();

    PeriodicEvent create(@Valid AdminHOPDCreatePeriodicEventRequest request);

    PeriodicEvent update(@Valid AdminHOPDUpdatePeriodicEventRequest request);

    String delete(String id);

    AdminHOPDDetailPeriodicEventCustom detail(String id);

}
