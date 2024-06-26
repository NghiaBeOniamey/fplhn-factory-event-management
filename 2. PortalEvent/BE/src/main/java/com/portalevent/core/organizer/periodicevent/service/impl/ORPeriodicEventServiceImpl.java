package com.portalevent.core.organizer.periodicevent.service.impl;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.organizer.periodicevent.model.request.ORFindPeriodicEventRequest;
import com.portalevent.core.organizer.periodicevent.model.response.ORDetailPeriodicEventCustom;
import com.portalevent.core.organizer.periodicevent.model.response.ORPeriodicEventMajorResponse;
import com.portalevent.core.organizer.periodicevent.model.response.ORPeriodicEventObjectResponse;
import com.portalevent.core.organizer.periodicevent.model.response.ORPeriodicEventResponse;
import com.portalevent.core.organizer.periodicevent.repository.ORPeriodicEventRepository;
import com.portalevent.core.organizer.periodicevent.service.ORPeriodicEventService;
import com.portalevent.entity.Event;
import com.portalevent.entity.EventMajor;
import com.portalevent.entity.EventObject;
import com.portalevent.entity.EventOrganizer;
import com.portalevent.entity.PeriodicEvent;
import com.portalevent.infrastructure.constant.EventRole;
import com.portalevent.infrastructure.constant.EventStatus;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.projection.SimpleEntityProjection;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.repository.EventMajorRepository;
import com.portalevent.repository.EventObjectRepository;
import com.portalevent.repository.EventOrganizerRepository;
import com.portalevent.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class ORPeriodicEventServiceImpl implements ORPeriodicEventService {

    private final ORPeriodicEventRepository orPeriodicEventRepository;

    private final EventRepository eventRepository;

    private final EventMajorRepository eventMajorRepository;

    private final EventObjectRepository eventObjectRepository;

    private final EventOrganizerRepository eventOrganizerRepository;

    private final PortalEventsSession session;

    public ORPeriodicEventServiceImpl(ORPeriodicEventRepository orPeriodicEventRepository, @Qualifier(EventRepository.NAME) EventRepository eventRepository, @Qualifier(EventMajorRepository.NAME) EventMajorRepository eventMajorRepository, @Qualifier(EventObjectRepository.NAME) EventObjectRepository eventObjectRepository, @Qualifier(EventOrganizerRepository.NAME) EventOrganizerRepository eventOrganizerRepository, PortalEventsSession session) {
        this.orPeriodicEventRepository = orPeriodicEventRepository;
        this.eventRepository = eventRepository;
        this.eventMajorRepository = eventMajorRepository;
        this.eventObjectRepository = eventObjectRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.session = session;
    }

    @Override
    public PageableObject<ORPeriodicEventResponse> getPage(final ORFindPeriodicEventRequest request) {
        String regexTrim = "\\s+";
        request.setName(request.getName().replaceAll(regexTrim, " "));
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return new PageableObject<>(orPeriodicEventRepository.getPage(pageable, request, session.getData()));
    }

    @Override
    public List<SimpleEntityProjection> getAllCategory() {
        return orPeriodicEventRepository.getAllCategory();
    }

    @Override
    public List<SimpleEntityProjection> getAllObject() {
        return orPeriodicEventRepository.getAllObject();
    }

    @Override
    public List<SimpleEntityProjection> getAllMajor() {
        return orPeriodicEventRepository.getAllMajor();
    }

    @Override
    public ORDetailPeriodicEventCustom detail(String id) {
        Optional<PeriodicEvent> periodicEventFind = orPeriodicEventRepository.findById(id);
        if (!periodicEventFind.isPresent()) {
            throw new RestApiException(Message.PERIODIC_EVENT_NOT_EXISTS);
        }
        ORDetailPeriodicEventCustom orDetailPeriodicEventCustom = new ORDetailPeriodicEventCustom();
        orDetailPeriodicEventCustom.setId(periodicEventFind.get().getId());
        orDetailPeriodicEventCustom.setName(periodicEventFind.get().getName());
        orDetailPeriodicEventCustom.setEventType(periodicEventFind.get().getEventType().ordinal());
        orDetailPeriodicEventCustom.setCategoryId(periodicEventFind.get().getCategoryId());
        orDetailPeriodicEventCustom.setDescription(periodicEventFind.get().getDescription());
        orDetailPeriodicEventCustom.setExpectedParticipants(periodicEventFind.get().getExpectedParticipants());
        orDetailPeriodicEventCustom.setListMajor(orPeriodicEventRepository.getAllMajorByIdPeriodicEvent(id));
        orDetailPeriodicEventCustom.setListObject(orPeriodicEventRepository.getAllObjectByIdPeriodicEvent(id));
        return orDetailPeriodicEventCustom;
    }

    @Override
    public Event registerPeriodicEvent(String idPeriodicEvent) {
        PeriodicEvent periodicEventFind = orPeriodicEventRepository.findById(idPeriodicEvent).get();
        if (periodicEventFind == null) {
            throw new RestApiException(Message.PERIODIC_EVENT_NOT_EXISTS);
        }
        Event newEvent = new Event();
        newEvent.setCategoryId(periodicEventFind.getCategoryId());
        newEvent.setDescription(periodicEventFind.getDescription());
        newEvent.setName(periodicEventFind.getName());
        newEvent.setStatus(EventStatus.SCHEDULED_HELD);
        newEvent.setIsAttendance(false);
        newEvent.setExpectedParticipants(periodicEventFind.getExpectedParticipants());
        newEvent.setEventType(periodicEventFind.getEventType());
        newEvent.setIsHireDesignBanner(false);
        newEvent.setIsHireDesignBg(false);
        newEvent.setIsHireDesignStandee(false);
        newEvent.setIsHireLocation(false);
        newEvent.setIsOpenRegistration(false);
        newEvent.setSubjectCode(session.getCurrentSubjectCode());
        newEvent.setTrainingFacilityCode(session.getCurrentTrainingFacilityCode());

        Event newSave = eventRepository.save(newEvent);
        EventOrganizer eventOrganizer = new EventOrganizer();
        eventOrganizer.setEventId(newSave.getId());
        eventOrganizer.setEventRole(EventRole.HOST);
        eventOrganizer.setOrganizerId(session.getCurrentUserCode());
        eventOrganizer.setOrganizerCode(session.getCurrentUserCode());
        eventOrganizerRepository.save(eventOrganizer);

        List<ORPeriodicEventMajorResponse> listMajorEventPeriodic = orPeriodicEventRepository.getAllEventMajorByIdPeriodicEvent(idPeriodicEvent );
        List<EventMajor> eventMajorList = new ArrayList<>();
        listMajorEventPeriodic.forEach(major -> {
            EventMajor eventMajor = new EventMajor();
            eventMajor.setEventId(newSave.getId());
            eventMajor.setMajorId(major.getMajorId());
            eventMajorList.add(eventMajor);
        });
        eventMajorRepository.saveAll(eventMajorList);

        List<ORPeriodicEventObjectResponse> listObjectEventPeriodic = orPeriodicEventRepository.getAllEventObjectByIdPeriodicEvent(idPeriodicEvent );
        List<EventObject> eventObjectList = new ArrayList<>();
        listObjectEventPeriodic.forEach(object -> {
            EventObject eventObject = new EventObject();
            eventObject.setEventId(newSave.getId());
            eventObject.setObjectId(object.getObjectId());
            eventObjectList.add(eventObject);
        });

        eventObjectRepository.saveAll(eventObjectList);
        return newSave;
    }
}
