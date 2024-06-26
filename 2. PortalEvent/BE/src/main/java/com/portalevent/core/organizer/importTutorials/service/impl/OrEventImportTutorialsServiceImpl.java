package com.portalevent.core.organizer.importTutorials.service.impl;

import com.portalevent.core.organizer.eventRegister.service.Impl.OerEventRegisterServiceImpl.ConstantLogMessage;
import com.portalevent.core.organizer.importTutorials.model.request.OrEventImportTutorialsFilterRequest;
import com.portalevent.core.organizer.importTutorials.model.request.OrEventImportTutorialsRequest;
import com.portalevent.core.organizer.importTutorials.model.request.OrEventListDataImportTutorialsRequest;
import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsMajorResponse;
import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsObjectResponse;
import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsResponse;
import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsResponseDTO;
import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsSemesterResponse;
import com.portalevent.core.organizer.importTutorials.repository.OrImportTutorialsEventLocationRepository;
import com.portalevent.core.organizer.importTutorials.repository.OrImportTutorialsEventMajorRepository;
import com.portalevent.core.organizer.importTutorials.repository.OrImportTutorialsEventObjectRepository;
import com.portalevent.core.organizer.importTutorials.repository.OrImportTutorialsEventOrganizerRepository;
import com.portalevent.core.organizer.importTutorials.repository.OrImportTutorialsEventRepository;
import com.portalevent.core.organizer.importTutorials.repository.OrImportTutorialsMajorRepository;
import com.portalevent.core.organizer.importTutorials.repository.OrImportTutorialsObjectRepository;
import com.portalevent.core.organizer.importTutorials.repository.OrImportTutorialsSemesterRepository;
import com.portalevent.core.organizer.importTutorials.service.OrEventImportTutorialsService;
import com.portalevent.entity.Event;
import com.portalevent.entity.EventLocation;
import com.portalevent.entity.EventMajor;
import com.portalevent.entity.EventObject;
import com.portalevent.entity.EventOrganizer;
import com.portalevent.entity.Major;
import com.portalevent.entity.Object;
import com.portalevent.entity.Semester;
import com.portalevent.infrastructure.constant.EventRole;
import com.portalevent.infrastructure.constant.EventStatus;
import com.portalevent.infrastructure.constant.EventType;
import com.portalevent.infrastructure.constant.Formality;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.LoggerUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrEventImportTutorialsServiceImpl implements OrEventImportTutorialsService {

    private final OrImportTutorialsEventRepository repository;

    private final OrImportTutorialsSemesterRepository semesterRepository;

    private final OrImportTutorialsEventOrganizerRepository organizerRepository;

    private final OrImportTutorialsMajorRepository majorRepository;

    private final OrImportTutorialsObjectRepository objectRepository;

    private final OrImportTutorialsEventMajorRepository eventMajorRepository;

    private final OrImportTutorialsEventObjectRepository eventObjectRepository;

    private final OrImportTutorialsEventLocationRepository eventLocationRepository;

    private final PortalEventsSession session;

    public OrEventImportTutorialsServiceImpl(OrImportTutorialsEventRepository repository, OrImportTutorialsSemesterRepository semesterRepository, OrImportTutorialsEventOrganizerRepository organizerRepository, OrImportTutorialsMajorRepository majorRepository, OrImportTutorialsObjectRepository objectRepository, OrImportTutorialsEventMajorRepository eventMajorRepository, OrImportTutorialsEventObjectRepository eventObjectRepository, OrImportTutorialsEventLocationRepository eventLocationRepository, PortalEventsSession session, LoggerUtil loggerUtil) {
        this.repository = repository;
        this.semesterRepository = semesterRepository;
        this.organizerRepository = organizerRepository;
        this.majorRepository = majorRepository;
        this.objectRepository = objectRepository;
        this.eventMajorRepository = eventMajorRepository;
        this.eventObjectRepository = eventObjectRepository;
        this.eventLocationRepository = eventLocationRepository;
        this.session = session;
        this.loggerUtil = loggerUtil;
    }

    @Override
    public List<OrImportTutorialsSemesterResponse> getAllSemester() {
        return semesterRepository.getAllSemester(session.getData());
    }

    private final LoggerUtil loggerUtil;

    @Override
    @Transactional
    public List<Event> importTutotials(OrEventImportTutorialsRequest request) {
        List<Event> listEvent = new ArrayList<>();
        List<Event> listEventDelete = repository.getALlEventDelete(request.getIdSemester() );
        for (Event e : listEventDelete) {
            repository.deleteById(e.getId());
            List<EventOrganizer> listEventOrganizerDelete = organizerRepository.findAll();
            for (EventOrganizer eventOrganizer: listEventOrganizerDelete) {
                if (eventOrganizer.getEventId().trim().equals(e.getId().trim())){
                    organizerRepository.delete(eventOrganizer);
                }
            }
            List<EventLocation> listEventLocation = eventLocationRepository.findEventLocationByEventId(e.getId());
            if (listEventLocation.size() > 0){
                for (EventLocation x: listEventLocation) {
                    eventLocationRepository.delete(x);
                }
            }

            List<EventMajor> listEventMajor = eventMajorRepository.findEventMajorByEventId(e.getId());
            if (listEventMajor.size() > 0){
                for (EventMajor x: listEventMajor) {
                    eventMajorRepository.delete(x);
                }
            }
            List<EventObject> listEventObject = eventObjectRepository.findEventObjectByEventId(e.getId());
            if (listEventObject.size() > 0){
                for (EventObject x: listEventObject) {
                    eventObjectRepository.delete(x);
                }
            }
        }

        List<OrEventListDataImportTutorialsRequest> listDataValid = request.getListDataImport();
        StringBuilder messageLog = new StringBuilder();
        for (OrEventListDataImportTutorialsRequest data: listDataValid) {
            Event newEvent = new Event();
            Long startTimeLongType = null; 
            Long endTimeLongType = null;
            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy hh:mm");
            try{
                if (!(data.getStartTime() == null) && !(data.getEndTime() == null)) {
                    Date startTime = sdf.parse(data.getStartTime());
                    Date endTime = sdf.parse(data.getEndTime());
                    startTimeLongType = startTime.getTime();
                    endTimeLongType = endTime.getTime();
                    if (startTime.compareTo(new Date()) < 0) {
                        throw new RestApiException(Message.INVALID_START_TIME); // Ngày bắt đầu không hợp lệ
                    }
                    if (endTime.compareTo(startTime) < 0
                            || endTime.compareTo(startTime) == 0) {
                        throw new RestApiException(Message.INVALID_END_TIME); // Ngày kết thúc không hợp lệ
                    }
                    if (data.getName().trim().isEmpty()){
                        throw new RestApiException(Message.EVENT_NAME_IS_REQUIRED); //Tên không được bỏ trống
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                throw new RestApiException(Message.INVALID_START_TIME); // Ngày BD hoặc KT không hợp lệ
            }
            newEvent.setSemesterId(request.getIdSemester().isEmpty() ? null : request.getIdSemester());
            newEvent.setName(data.getName());
            newEvent.setStatus(EventStatus.SCHEDULED_HELD);
            newEvent.setIsAttendance(false);
            newEvent.setEventType(EventType.TUTORIAL);
            newEvent.setBlockNumber(request.getBlockNumber());
            newEvent.setStartTime(startTimeLongType);
            newEvent.setEndTime(endTimeLongType);
            newEvent.setIsHireDesignBanner(false);
            newEvent.setIsHireDesignBg(false);
            newEvent.setIsHireDesignStandee(false);
            newEvent.setIsHireLocation(false);
            newEvent.setIsOpenRegistration(false);
            newEvent.setSubjectCode(session.getCurrentSubjectCode());
            newEvent.setTrainingFacilityCode(session.getCurrentTrainingFacilityCode());
            //save event
            Event event = repository.save(newEvent);
            //add event
            listEvent.add(event);
            String semesterNm = "";
            for (Semester x : semesterRepository.findAll()) {
                if (x.getId().equals(request.getIdSemester())){
                    semesterNm = x.getName();
                }
            }
            messageLog.append("Tạo sự kiện thành công ")
                    .append(ConstantLogMessage.NAME + ConstantLogMessage.WHITE_SPACE + event.getName() + ConstantLogMessage.WHITE_SPACE)
                    .append(ConstantLogMessage.SEMESTER +  ConstantLogMessage.WHITE_SPACE + semesterNm + ConstantLogMessage.WHITE_SPACE)
                    .append(ConstantLogMessage.EVENTYPE + event.getEventType() == null ? ConstantLogMessage.UNKNOWN : event.getEventType() == EventType.STUDENT_EVENT ? ConstantLogMessage.STUDENT : event.getEventType() == EventType.LECTURER_EVENT ? ConstantLogMessage.LECTURER : event.getEventType() == EventType.COMBINED_EVNT ? ConstantLogMessage.COMBINED : ConstantLogMessage.TUTORIAL + ConstantLogMessage.WHITE_SPACE)
                    .append(ConstantLogMessage.TIME + ConstantLogMessage.WHITE_SPACE + data.getStartTime()).append(ConstantLogMessage.HYPHEN + data.getEndTime() + ConstantLogMessage.WHITE_SPACE)
            ;
            EventOrganizer eventOrganizer = new EventOrganizer();
            eventOrganizer.setEventId(event.getId());
            eventOrganizer.setEventRole(EventRole.HOST);
            eventOrganizer.setOrganizerId(session.getCurrentIdUser());
            eventOrganizer.setOrganizerCode(session.getCurrentUserCode());
            //save event organizer
            organizerRepository.save(eventOrganizer);

            String listMajor[] = data.getMajorStr().split(",");
            messageLog.append(ConstantLogMessage.MAJOR);
            for (String majorName : listMajor) {
                Optional<OrImportTutorialsMajorResponse> major = majorRepository.getMajorByName(majorName.trim());
                EventMajor eventMajor = new EventMajor();
                eventMajor.setEventId(event.getId());
                if (major.isPresent()){
                    eventMajor.setMajorId(major.get().getId());
                    messageLog.append(ConstantLogMessage.WHITE_SPACE + major.get().getName() + ConstantLogMessage.WHITE_SPACE);
                }else{
                    eventMajor.setMajorId("");
                    messageLog.append(ConstantLogMessage.WHITE_SPACE + ConstantLogMessage.UNKNOWN + ConstantLogMessage.WHITE_SPACE);
                }
                eventMajorRepository.save(eventMajor);

            }
            String listObject[] = data.getObjectStr().split(",");
            messageLog.append(ConstantLogMessage.OBJECT);
            for (String objectName : listObject) {
                Optional<OrImportTutorialsObjectResponse> object = objectRepository.getObjectByName(objectName.trim());
                EventObject eventObject = new EventObject();
                eventObject.setEventId(event.getId());
                if (object.isPresent()){
                    eventObject.setObjectId(object.get().getId());
                    messageLog.append(ConstantLogMessage.WHITE_SPACE + object.get().getName() + ConstantLogMessage.WHITE_SPACE);
                }
                else {
                    eventObject.setObjectId("");
                    messageLog.append(ConstantLogMessage.WHITE_SPACE + ConstantLogMessage.UNKNOWN + ConstantLogMessage.WHITE_SPACE);
                }
                //save event object
                eventObjectRepository.save(eventObject);
            }

            String listLocation[] = data.getLocationStr().split(",");
            messageLog.append(ConstantLogMessage.LOCATION);
            for (String locationPath : listLocation) {
                EventLocation eventLocation =  new EventLocation();
                eventLocation.setPath(locationPath.trim());
                eventLocation.setFormality(Formality.ONLINE);
                eventLocation.setEventId(event.getId());
                eventLocation.setName(generateEventName(event.getName()) + event.getStartTime());
                eventLocationRepository.save(eventLocation);
                messageLog.append(ConstantLogMessage.WHITE_SPACE + eventLocation.getName() + ConstantLogMessage.WHITE_SPACE);
                loggerUtil.sendLog(messageLog.toString(), event.getId());
            }
        }
        return listEvent;
    }

    @Override
    public List<OrImportTutorialsResponseDTO> getAllTutorials(OrEventImportTutorialsFilterRequest request) {
        request.setIdOrganizer(session.getCurrentIdUser());
        List<OrImportTutorialsResponse> listTutorials = repository.getAll(request,session.getData());
        List<OrImportTutorialsResponseDTO> listResponse = new ArrayList<>();
        List<EventLocation> listEventLocation = eventLocationRepository.findAll();
        for (OrImportTutorialsResponse x: listTutorials) {
            OrImportTutorialsResponseDTO dto = new OrImportTutorialsResponseDTO();
            Optional<Major> major = majorRepository.findById(x.getEventMajorId());
            Optional<Object> object = objectRepository.findById(x.getEventObjectId());
            dto.setTutorial(x);
            if (major.isPresent() && object.isPresent()){
                dto.setMajorName(major.get().getName());
                dto.setObjectName(object.get().getName());
            }
            else {
                dto.setMajorName("--");
                dto.setObjectName("--");
            }
            String locationPathStr = "";
            for (EventLocation e : listEventLocation) {
                if (e.getEventId().equals(x.getId())){
                    locationPathStr += e.getPath() + ",";
                }
                dto.setLocationPath(locationPathStr);
            }
            if (dto.getLocationPath() == null){
                dto.setLocationPath("");
            }
            listResponse.add(dto);
        }
        return listResponse;
    }
    private String generateEventName(String name) {
        String arr[] = name.split(" ");
        String key = "";
        for (int i = 0; i < arr.length; i++) {
            key += arr[i].substring(0, 1).toUpperCase();
        }
        return key;
    }
    private List<EventLocation> getAllLocation() {
        return eventLocationRepository.findAll();
    }

}
