package com.portalevent.core.approver.periodicevent.service.impl;

import com.portalevent.core.approver.periodicevent.model.request.APDCreatePeriodicEventRequest;
import com.portalevent.core.approver.periodicevent.model.request.APDFindPeriodEventRequest;
import com.portalevent.core.approver.periodicevent.model.request.APDUpdatePeriodicEventRequest;
import com.portalevent.core.approver.periodicevent.model.response.APDDetailPeriodicEventCustom;
import com.portalevent.core.approver.periodicevent.model.response.APDPeriodicEventResponse;
import com.portalevent.core.approver.periodicevent.repository.APDPeriodicEventRepository;
import com.portalevent.core.approver.periodicevent.service.APDPeriodicEventService;
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
import org.springframework.beans.factory.annotation.Autowired;
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
public class APDPeriodicEventServiceImpl implements APDPeriodicEventService {

    @Autowired
    private APDPeriodicEventRepository apdPeriodicEventRepository;

    @Autowired
    @Qualifier(PeriodicEventObjectRepository.NAME)
    private PeriodicEventObjectRepository periodicEventObjectRepository;

    @Autowired
    @Qualifier(PeriodicEventMajorRepository.NAME)
    private PeriodicEventMajorRepository periodicEventMajorRepository;

    @Autowired
    private PortalEventsSession session;

    @Override
    public PageableObject<APDPeriodicEventResponse> getPage(final APDFindPeriodEventRequest request) {
        String regexTrim = "\\s+";
        request.setName((request.getName().replaceAll(regexTrim, " ")));
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        return new PageableObject<>(apdPeriodicEventRepository.getPage(pageable, request));
    }

    @Override
    public PageableObject<APDPeriodicEventResponse> getPageEventWaitApprover(APDFindPeriodEventRequest request) {
        String regexTrim = "\\s+";
        request.setName((request.getName().replaceAll(regexTrim, " ")));
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
    public PeriodicEvent create(@Valid APDCreatePeriodicEventRequest request) {
        String regexTrim = "\\s+";
        request.setName((request.getName().replaceAll(regexTrim, " ")));
        request.setDescription(request.getDescription().replaceAll(regexTrim, " "));
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
    public PeriodicEvent update(@Valid APDUpdatePeriodicEventRequest request) {
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
    public APDDetailPeriodicEventCustom detail(String id) {
        Optional<PeriodicEvent> periodicEventFind = apdPeriodicEventRepository.findById(id);
        if (!periodicEventFind.isPresent()) {
            throw new RestApiException(Message.PERIODIC_EVENT_NOT_EXISTS);
        }
        APDDetailPeriodicEventCustom apdDetailPeriodicEventCustom = new APDDetailPeriodicEventCustom();
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
