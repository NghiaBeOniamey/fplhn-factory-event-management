package com.portalevent.core.adminho.periodiceventmanagement.service.impl;

import com.portalevent.core.adminho.periodiceventmanagement.model.request.AdminHOPDCreatePeriodicEventRequest;
import com.portalevent.core.adminho.periodiceventmanagement.model.request.AdminHOPDFindPeriodEventRequest;
import com.portalevent.core.adminho.periodiceventmanagement.model.request.AdminHOPDUpdatePeriodicEventRequest;
import com.portalevent.core.adminho.periodiceventmanagement.model.response.AdminHOPDDetailPeriodicEventCustom;
import com.portalevent.core.adminho.periodiceventmanagement.model.response.AdminHOPDPeriodicEventResponse;
import com.portalevent.core.adminho.periodiceventmanagement.repository.AdminHOPDPeriodicEventRepository;
import com.portalevent.core.adminho.periodiceventmanagement.service.AdminHOPDPeriodicEventService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.PeriodicEvent;
import com.portalevent.entity.PeriodicEventMajor;
import com.portalevent.entity.PeriodicEventObject;
import com.portalevent.infrastructure.constant.EventType;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.projection.SimpleEntityProjection;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.repository.PeriodicEventMajorRepository;
import com.portalevent.repository.PeriodicEventObjectRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Service
@Validated
public class AdminHOPDPeriodicEventServiceImpl implements AdminHOPDPeriodicEventService {

    private final AdminHOPDPeriodicEventRepository apdPeriodicEventRepository;

    private final PeriodicEventObjectRepository periodicEventObjectRepository;

    private final PeriodicEventMajorRepository periodicEventMajorRepository;

    private final PortalEventsSession session;

    public AdminHOPDPeriodicEventServiceImpl(AdminHOPDPeriodicEventRepository apdPeriodicEventRepository, @Qualifier(PeriodicEventObjectRepository.NAME) PeriodicEventObjectRepository periodicEventObjectRepository, @Qualifier(PeriodicEventMajorRepository.NAME) PeriodicEventMajorRepository periodicEventMajorRepository, PortalEventsSession session) {
        this.apdPeriodicEventRepository = apdPeriodicEventRepository;
        this.periodicEventObjectRepository = periodicEventObjectRepository;
        this.periodicEventMajorRepository = periodicEventMajorRepository;
        this.session = session;
    }

    @Override
    public PageableObject<AdminHOPDPeriodicEventResponse> getPage(final AdminHOPDFindPeriodEventRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return new PageableObject<>(apdPeriodicEventRepository.getPage(pageable, request));
    }

    @Override
    public PageableObject<AdminHOPDPeriodicEventResponse> getPageEventWaitApprover(AdminHOPDFindPeriodEventRequest request) {
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return new PageableObject<>(apdPeriodicEventRepository.getPageEventWaitApprover(pageable, request, session.getData()));
    }

    @Override
    public List<SimpleEntityProjection> getAllCategory() {
        return apdPeriodicEventRepository.getAllCategory();
    }

    @Override
    public List<SimpleEntityProjection> getAllObject() {
        return apdPeriodicEventRepository.getAllObject();
    }

    @Override
    public List<SimpleEntityProjection> getAllMajor() {
        return apdPeriodicEventRepository.getAllMajor();
    }

    @Override
    public PeriodicEvent create(@Valid AdminHOPDCreatePeriodicEventRequest request) {
        PeriodicEvent periodicEvent = new PeriodicEvent();
        periodicEvent.setName(request.getName() != null ? request.getName().trim() : null);
        periodicEvent.setEventType(EventType.values()[request.getEventType()]);
        periodicEvent.setCategoryId(request.getCategoryId());
        periodicEvent.setExpectedParticipants(request.getExpectedParticipants());
        periodicEvent.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        periodicEvent.setSubjectCode(session.getCurrentSubjectCode());
        periodicEvent.setTrainingFacilityCode(session.getCurrentTrainingFacilityCode());
        PeriodicEvent periodicEventNew = apdPeriodicEventRepository.save(periodicEvent);
        List<PeriodicEventMajor> periodicEventMajors = new ArrayList<>();
        request.getListMajor().forEach(major -> {
            PeriodicEventMajor periodicEventMajor = new PeriodicEventMajor();
            periodicEventMajor.setPeriodicEventId(periodicEventNew.getId());
            periodicEventMajor.setMajorId(major);
            periodicEventMajors.add(periodicEventMajor);
        });
        periodicEventMajorRepository.saveAll(periodicEventMajors);
        List<PeriodicEventObject> periodicEventObjects = new ArrayList<>();
        request.getListObject().forEach(object -> {
            PeriodicEventObject periodicEventObject = new PeriodicEventObject();
            periodicEventObject.setPeriodicEventId(periodicEventNew.getId());
            periodicEventObject.setObjectId(object);
            periodicEventObjects.add(periodicEventObject);
        });
        periodicEventObjectRepository.saveAll(periodicEventObjects);
        return periodicEventNew;
    }

    @Override
    public PeriodicEvent update(@Valid AdminHOPDUpdatePeriodicEventRequest request) {
        PeriodicEvent periodicEvent = apdPeriodicEventRepository.findById(request.getId()).get();
        if (periodicEvent == null) {
            throw new RestApiException(Message.PERIODIC_EVENT_NOT_EXISTS);
        }
        periodicEvent.setName(request.getName() != null ? request.getName().trim() : null);
        periodicEvent.setEventType(EventType.values()[request.getEventType()]);
        periodicEvent.setCategoryId(request.getCategoryId());
        periodicEvent.setExpectedParticipants(request.getExpectedParticipants());
        periodicEvent.setDescription(request.getDescription() != null ? request.getDescription().trim() : null);
        PeriodicEvent periodicEventUpdate = apdPeriodicEventRepository.save(periodicEvent);
        apdPeriodicEventRepository.deletePeriodicEventMajor(request.getId());
        apdPeriodicEventRepository.deletePeriodicEventObject(request.getId());
        List<PeriodicEventMajor> periodicEventMajors = new ArrayList<>();
        request.getListMajor().forEach(major -> {
            PeriodicEventMajor periodicEventMajor = new PeriodicEventMajor();
            periodicEventMajor.setPeriodicEventId(periodicEventUpdate.getId());
            periodicEventMajor.setMajorId(major);
            periodicEventMajors.add(periodicEventMajor);
        });
        periodicEventMajorRepository.saveAll(periodicEventMajors);
        List<PeriodicEventObject> periodicEventObjects = new ArrayList<>();
        request.getListObject().forEach(object -> {
            PeriodicEventObject periodicEventObject = new PeriodicEventObject();
            periodicEventObject.setPeriodicEventId(periodicEventUpdate.getId());
            periodicEventObject.setObjectId(object);
            periodicEventObjects.add(periodicEventObject);
        });
        periodicEventObjectRepository.saveAll(periodicEventObjects);
        return periodicEventUpdate;
    }

    @Override
    @Transactional
    public String delete(String id) {
        try {
            Optional<PeriodicEvent> periodicEventFind = apdPeriodicEventRepository.findById(id);
            if (!periodicEventFind.isPresent()) {
                throw new RestApiException(Message.PERIODIC_EVENT_NOT_EXISTS);
            }
            apdPeriodicEventRepository.deletePeriodicEventMajor(id);
            apdPeriodicEventRepository.deletePeriodicEventObject(id);
            apdPeriodicEventRepository.delete(periodicEventFind.get());
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AdminHOPDDetailPeriodicEventCustom detail(String id) {
        Optional<PeriodicEvent> periodicEventFind = apdPeriodicEventRepository.findById(id);
        if (!periodicEventFind.isPresent()) {
            throw new RestApiException(Message.PERIODIC_EVENT_NOT_EXISTS);
        }
        AdminHOPDDetailPeriodicEventCustom apdDetailPeriodicEventCustom = new AdminHOPDDetailPeriodicEventCustom();
        apdDetailPeriodicEventCustom.setId(periodicEventFind.get().getId());
        apdDetailPeriodicEventCustom.setName(periodicEventFind.get().getName());
        apdDetailPeriodicEventCustom.setEventType(periodicEventFind.get().getEventType().ordinal());
        apdDetailPeriodicEventCustom.setCategoryId(periodicEventFind.get().getCategoryId());
        apdDetailPeriodicEventCustom.setDescription(periodicEventFind.get().getDescription());
        apdDetailPeriodicEventCustom.setExpectedParticipants(periodicEventFind.get().getExpectedParticipants());
        apdDetailPeriodicEventCustom.setListMajor(apdPeriodicEventRepository.getAllMajorByIdPeriodicEvent(id));
        apdDetailPeriodicEventCustom.setListObject(apdPeriodicEventRepository.getAllObjectByIdPeriodicEvent(id));
        return apdDetailPeriodicEventCustom;
    }

}
