package com.portalevent.core.organizer.eventRegistered.service.impl;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.organizer.eventRegistered.model.request.OerdFilterEventRequest;
import com.portalevent.core.organizer.eventRegistered.model.request.OerdFindEventRequest;
import com.portalevent.core.organizer.eventRegistered.model.response.*;
import com.portalevent.core.organizer.eventRegistered.repository.OerdCategoryRepository;
import com.portalevent.core.organizer.eventRegistered.repository.OerdEventOrganizerRepository;
import com.portalevent.core.organizer.eventRegistered.repository.OerdEventRepository;
import com.portalevent.core.organizer.eventRegistered.repository.OerdSemesterRepository;
import com.portalevent.core.organizer.eventRegistered.service.OerdEventRegisteredService;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.CallApiIdentity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SonPT
 */

@Service
@Slf4j
public class OerdEventRegisteredServiceImpl implements OerdEventRegisteredService {

    private final OerdCategoryRepository categoryRepository;

    private final OerdSemesterRepository semesterRepository;

    private final OerdEventRepository eventRepository;

    private final OerdEventOrganizerRepository eventOrganizerRepository;

    private final CallApiIdentity callApiIdentity;

    private final PortalEventsSession session;

    public OerdEventRegisteredServiceImpl(OerdCategoryRepository categoryRepository, OerdSemesterRepository semesterRepository, OerdEventRepository eventRepository, OerdEventOrganizerRepository eventOrganizerRepository, CallApiIdentity callApiIdentity, PortalEventsSession session) {
        this.categoryRepository = categoryRepository;
        this.semesterRepository = semesterRepository;
        this.eventRepository = eventRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.callApiIdentity = callApiIdentity;
        this.session = session;
    }

    /**
     * @param:
     * @return: OerdCategoryResponse
     * */
    @Override
    public List<OerdCategoryResponse> getAll() {
        return categoryRepository.getAll();
    }

    /**
     * @param:
     * @return: OerdSemesterResponse
     * */
    @Override
    public List<OerdSemesterResponse> getAllSemester() {
        return semesterRepository.getAll();
    }

    /**
     * @param: OerdFindEventRequest
     * @return: OerdEventResponseDTO
     * */
    @Override
    public PageableObject<OerdEventResponseDTO> getAllEventByIdOrganizer(final OerdFindEventRequest req) {
        String regexTrim = "\\s+";
        Long startTimeLong = null;
        Long endTimeLong = null;
        if (req.getStartTime() != null && req.getEndTime() != null
                && !req.getStartTime().equals("null") && !req.getEndTime().equals("null")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Date startTime = null;
            Date endTime = null;
            try {
                startTime = sdf.parse(req.getStartTime());
                endTime = sdf.parse(req.getEndTime());
            } catch (ParseException e) {
                e.printStackTrace();
                throw new RestApiException("2001"); // Ngày không hợp lệ
            }
            startTimeLong = startTime.getTime();
            endTimeLong = endTime.getTime();
        }
        OerdFilterEventRequest omFilterEventRequest = new OerdFilterEventRequest();
        omFilterEventRequest.setIdOrganizer(session.getCurrentUserCode()); //get Id Organizer from current session
        omFilterEventRequest.setIdCategory(req.getIdCategory());
        omFilterEventRequest.setIdSemester(req.getIdSemester());
        omFilterEventRequest.setFormality(req.getFormality().trim().equals("-1") ? null : req.getFormality().equals("0") ? (short) 0 : (short) 1);
                omFilterEventRequest.setName(req.getName());
        omFilterEventRequest.setStatus(req.getStatus());
        if (req.getStartTime() != null && req.getEndTime() != null) {
            omFilterEventRequest.setEndTime(endTimeLong);
            omFilterEventRequest.setStartTime(startTimeLong);
        } else {
            omFilterEventRequest.setEndTime(null);
            omFilterEventRequest.setStartTime(null);
        }
        omFilterEventRequest.setStatusSort(req.getStatusSort());

        Pageable pageable = PageRequest.of(req.getPage(), 5);

        Page<OerdEventResponse> listEventResult;

        if(req.getIsMe()){
            listEventResult = eventRepository.getAllEventIsMe(pageable, omFilterEventRequest, session.getData());
        }else{
            listEventResult = eventRepository.getAllEventByIdOrganizer(pageable, omFilterEventRequest, session.getData());
        }

        PageableObject pageableObject = new PageableObject(listEventResult);
        List<OerdEventResponseDTO> listResponse = new ArrayList<>();
        for (OerdEventResponse x : listEventResult) {
            List<String> listIdOrganizer =
                eventOrganizerRepository.getListOrganizerId(x.getId() )
                .stream()
                .map(s -> {
                    return s.getIdOrganizer();
                })
                .distinct()
                .collect(Collectors.toList())
                ;
            String listUserNameOrganizer = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer)
                    .stream()
                    .map(SimpleResponse::getUserName)
                    .toList()
                    .toString();
            listUserNameOrganizer = listUserNameOrganizer.substring(1, listUserNameOrganizer.length() - 1);
            listResponse.add(new OerdEventResponseDTO(x, getListLocation(x.getId()), listUserNameOrganizer.isEmpty() ? "--" : listUserNameOrganizer));
        }
        pageableObject.setData(listResponse);
        return pageableObject;
    }

    /**
     * @param: String idEvent
     * @return: OerdLocationResponse
     * get list from table event location by id event
     * */
    @Override
    public List<OerdLocationResponse> getListLocation(String idEvent) {
        return eventRepository.getLocationByEventId(idEvent );
    }
}
