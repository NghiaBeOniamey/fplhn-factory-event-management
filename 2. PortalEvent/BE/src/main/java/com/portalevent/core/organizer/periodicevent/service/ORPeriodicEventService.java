package com.portalevent.core.organizer.periodicevent.service;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.periodicevent.model.request.ORFindPeriodicEventRequest;
import com.portalevent.core.organizer.periodicevent.model.response.ORDetailPeriodicEventCustom;
import com.portalevent.core.organizer.periodicevent.model.response.ORPeriodicEventResponse;
import com.portalevent.entity.Event;
import com.portalevent.infrastructure.projection.SimpleEntityProjection;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface ORPeriodicEventService {

    PageableObject<ORPeriodicEventResponse> getPage(final ORFindPeriodicEventRequest request);

    List<SimpleEntityProjection> getAllCategory();

    List<SimpleEntityProjection> getAllObject();

    List<SimpleEntityProjection> getAllMajor();

    ORDetailPeriodicEventCustom detail(String id);

    Event registerPeriodicEvent(String idPeriodicEvent);
}
