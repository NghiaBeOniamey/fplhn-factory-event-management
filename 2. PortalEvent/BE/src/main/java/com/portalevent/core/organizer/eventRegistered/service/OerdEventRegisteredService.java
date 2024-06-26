package com.portalevent.core.organizer.eventRegistered.service;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.organizer.eventRegistered.model.request.OerdFindEventRequest;
import com.portalevent.core.organizer.eventRegistered.model.response.OerdCategoryResponse;
import com.portalevent.core.organizer.eventRegistered.model.response.OerdEventResponseDTO;
import com.portalevent.core.organizer.eventRegistered.model.response.OerdLocationResponse;
import com.portalevent.core.organizer.eventRegistered.model.response.OerdSemesterResponse;

import java.util.List;

/**
 * @author SonPT
 */
public interface OerdEventRegisteredService {

    List<OerdCategoryResponse> getAll();

    List<OerdSemesterResponse> getAllSemester();

    PageableObject<OerdEventResponseDTO> getAllEventByIdOrganizer(OerdFindEventRequest req);

    List<OerdLocationResponse> getListLocation(String idEvent);

}
