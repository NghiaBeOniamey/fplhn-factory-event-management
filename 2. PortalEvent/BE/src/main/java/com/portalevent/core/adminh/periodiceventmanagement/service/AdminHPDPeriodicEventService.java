package com.portalevent.core.adminh.periodiceventmanagement.service;

import com.portalevent.core.adminh.periodiceventmanagement.model.request.AdminHPDCreatePeriodicEventRequest;
import com.portalevent.core.adminh.periodiceventmanagement.model.request.AdminHPDFindPeriodEventRequest;
import com.portalevent.core.adminh.periodiceventmanagement.model.request.AdminHPDUpdatePeriodicEventRequest;
import com.portalevent.core.adminh.periodiceventmanagement.model.response.AdminHPDDetailPeriodicEventCustom;
import com.portalevent.core.adminh.periodiceventmanagement.model.response.AdminHPDPeriodicEventResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.PeriodicEvent;
import com.portalevent.infrastructure.projection.SimpleEntityProjection;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdminHPDPeriodicEventService {

    PageableObject<AdminHPDPeriodicEventResponse> getPage(final AdminHPDFindPeriodEventRequest request);

    PageableObject<AdminHPDPeriodicEventResponse> getPageEventWaitApprover(final AdminHPDFindPeriodEventRequest request);

    List<SimpleEntityProjection> getAllCategory();

    List<SimpleEntityProjection> getAllObject();

    List<SimpleEntityProjection> getAllMajor();

    PeriodicEvent create(@Valid AdminHPDCreatePeriodicEventRequest request);

    PeriodicEvent update(@Valid AdminHPDUpdatePeriodicEventRequest request);

    String delete(String id);

    AdminHPDDetailPeriodicEventCustom detail(String id);

}
