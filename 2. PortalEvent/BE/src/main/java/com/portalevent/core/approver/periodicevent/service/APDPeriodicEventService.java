package com.portalevent.core.approver.periodicevent.service;

import com.portalevent.core.approver.periodicevent.model.request.APDCreatePeriodicEventRequest;
import com.portalevent.core.approver.periodicevent.model.request.APDFindPeriodEventRequest;
import com.portalevent.core.approver.periodicevent.model.request.APDUpdatePeriodicEventRequest;
import com.portalevent.core.approver.periodicevent.model.response.APDDetailPeriodicEventCustom;
import com.portalevent.core.approver.periodicevent.model.response.APDPeriodicEventResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.PeriodicEvent;
import com.portalevent.infrastructure.projection.SimpleEntityProjection;
import jakarta.validation.Valid;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface APDPeriodicEventService {

    PageableObject<APDPeriodicEventResponse> getPage(final APDFindPeriodEventRequest request);

    PageableObject<APDPeriodicEventResponse> getPageEventWaitApprover(final APDFindPeriodEventRequest request);

    List<SimpleEntityProjection> getAllCategory();

    List<SimpleEntityProjection> getAllObject();

    List<SimpleEntityProjection> getAllMajor();

    PeriodicEvent create(@Valid APDCreatePeriodicEventRequest request);

    PeriodicEvent update(@Valid APDUpdatePeriodicEventRequest request);

    String delete(String id);

    APDDetailPeriodicEventCustom detail(String id);
}
