package com.portalevent.core.organizer.eventRegister.service.Impl;

import com.portalevent.core.organizer.eventRegister.model.request.OerCreateCategoryRequest;
import com.portalevent.core.organizer.eventRegister.model.request.OerCreateEventLocationRequest;
import com.portalevent.core.organizer.eventRegister.model.request.OerCreateEventRequest;
import com.portalevent.core.organizer.eventRegister.model.response.OerCategoryResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerDepartmentCampusResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerDepartmentResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerMajorCampusResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerMajorResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerObjectResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerSemesterResponse;
import com.portalevent.core.organizer.eventRegister.repository.OerCategoryRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerDepartmentCampusRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerDepartmentRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerEventMajorRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerEventObjectRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerEventOrganizeRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerEventRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerMajorCampusRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerMajorRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerObjectRepository;
import com.portalevent.core.organizer.eventRegister.repository.OerSemesterRepository;
import com.portalevent.core.organizer.eventRegister.service.OerEventRegisterService;
import com.portalevent.entity.Category;
import com.portalevent.entity.Event;
import com.portalevent.entity.EventLocation;
import com.portalevent.entity.EventMajor;
import com.portalevent.entity.EventObject;
import com.portalevent.entity.EventOrganizer;
import com.portalevent.infrastructure.constant.EventRole;
import com.portalevent.infrastructure.constant.EventStatus;
import com.portalevent.infrastructure.constant.EventType;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.LoggerUtil;
import jakarta.validation.Valid;
import lombok.Synchronized;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author SonPT
 */

@Service
public class OerEventRegisterServiceImpl implements OerEventRegisterService {

    public OerEventRegisterServiceImpl(OerCategoryRepository categoryRepository, OerMajorRepository majorRepository, OerEventRepository omEventRepository, OerEventOrganizeRepository omEventOrganierRepository, OerEventMajorRepository eventMajorRepository, OerObjectRepository objectRepository, OerSemesterRepository semesterRepository, OerEventObjectRepository eventObjectRepository, OerDepartmentRepository departmentRepository, OerDepartmentCampusRepository departmentCampusRepository, OerMajorCampusRepository majorCampusRepository, PortalEventsSession session, LoggerUtil loggerUtil) {
        this.categoryRepository = categoryRepository;
        this.majorRepository = majorRepository;
        this.omEventRepository = omEventRepository;
        this.omEventOrganierRepository = omEventOrganierRepository;
        this.eventMajorRepository = eventMajorRepository;
        this.objectRepository = objectRepository;
        this.semesterRepository = semesterRepository;
        this.eventObjectRepository = eventObjectRepository;
        this.departmentRepository = departmentRepository;
        this.departmentCampusRepository = departmentCampusRepository;
        this.majorCampusRepository = majorCampusRepository;
        this.session = session;
        this.loggerUtil = loggerUtil;
    }

    public class ConstantLogMessage {
        public final static String NAME = "Tên";
        public final static String TIME = "Thời gian";
        public final static String SEMESTER = "Học kỳ";
        public final static String STUDENT = "Sinh viên";
        public final static String LECTURER = "Giảng viên";
        public final static String COMBINED = "Tất cả";
        public final static String TUTORIAL = "Tutorial";
        public final static String UNKNOWN = "Chưa có";
        public final static String MAJOR = "bộ môn";
        public final static String EVENTYPE = "Event type";
        public final static String OBJECT = "Đối tượng";
        public final static String CATEGORY = "Thể loại";
        public final static String LOCATION = "Địa điểm";
        public final static String BLOCK_1 = "Block 1";
        public final static String BLOCK_2 = "Block 2";
        public final static String WHITE_SPACE = " ";
        public final static String HYPHEN = " - ";
    }

    private final OerCategoryRepository categoryRepository;

    private final OerMajorRepository majorRepository;

    private final OerEventRepository omEventRepository;

    private final OerEventOrganizeRepository omEventOrganierRepository;

    private final OerEventMajorRepository eventMajorRepository;

    private final OerObjectRepository objectRepository;

    private final OerSemesterRepository semesterRepository;

    private final OerEventObjectRepository eventObjectRepository;

    private final OerDepartmentRepository departmentRepository;

    private final OerDepartmentCampusRepository departmentCampusRepository;

    private final OerMajorCampusRepository majorCampusRepository;

    private final PortalEventsSession session;

    private final LoggerUtil loggerUtil;

    /**
     * @param: OerCreateEventRequest
     * @param: Event
     * */

    @Override
    @Synchronized
    public Event create(@Valid OerCreateEventRequest request) { //event register
        String regexTrim = "\\s+";
        request.setName(request.getName().replaceAll(regexTrim, " "));
        request.setDescription(request.getDescription().replaceAll(regexTrim, " "));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Event newEvent = new Event();
        StringBuilder messageLog = new StringBuilder();
        this.newEventDTO(request, newEvent);
        //save event
        Event event = omEventRepository.save(newEvent);
        String startTimeStr = sdf.format(event.getStartTime());
        String endTimeStr = sdf.format(event.getEndTime());
        String semesterNm = "";
        semesterNm = getSemesterNm(event, semesterNm);
        String categoryNm = "";
        categoryNm = getCategoryNm(event, categoryNm);
        messageLog.append("Tạo sự kiện thành công ")
                .append(ConstantLogMessage.NAME + ConstantLogMessage.WHITE_SPACE + event.getName() + ConstantLogMessage.WHITE_SPACE)
                .append(ConstantLogMessage.SEMESTER +  ConstantLogMessage.WHITE_SPACE + semesterNm + ConstantLogMessage.WHITE_SPACE)
                .append(event.getBlockNumber() ? ConstantLogMessage.BLOCK_2 : ConstantLogMessage.BLOCK_1)
                .append(ConstantLogMessage.CATEGORY + categoryNm)
                .append(ConstantLogMessage.EVENTYPE + event.getEventType() == null ? ConstantLogMessage.UNKNOWN : event.getEventType() == EventType.STUDENT_EVENT ? ConstantLogMessage.STUDENT : event.getEventType() == EventType.LECTURER_EVENT ? ConstantLogMessage.LECTURER : event.getEventType() == EventType.COMBINED_EVNT ? ConstantLogMessage.COMBINED : ConstantLogMessage.TUTORIAL + ConstantLogMessage.WHITE_SPACE)
                .append(ConstantLogMessage.TIME + ConstantLogMessage.WHITE_SPACE + startTimeStr).append(ConstantLogMessage.HYPHEN + endTimeStr + ConstantLogMessage.WHITE_SPACE)
        ;

        EventOrganizer eventOrganizer = getEventOrganizer(event);
        //save event organizer
        omEventOrganierRepository.save(eventOrganizer);
        messageLog.append(ConstantLogMessage.MAJOR);
        if(request.getListMajor().length > 0){
            List<EventMajor> list = getEventMajors(request, event, messageLog);
            //save event Major
            eventMajorRepository.saveAll(list);
        }

        messageLog.append(ConstantLogMessage.OBJECT);
        if(request.getListObject().length > 0){
            List<EventObject> list = getEventObjects(request, event, messageLog);
            //save event object
            eventObjectRepository.saveAll(list);
        }
        loggerUtil.sendLog(messageLog.toString(), event.getId());
        return event;
    }

    private EventOrganizer getEventOrganizer(Event event) {
        EventOrganizer eventOrganizer = new EventOrganizer();
        eventOrganizer.setEventId(event.getId());
        eventOrganizer.setEventRole(EventRole.HOST);
        eventOrganizer.setOrganizerId(session.getCurrentUserCode());
        eventOrganizer.setOrganizerCode(session.getCurrentUserCode());
        eventOrganizer.setMeetingHour(2.0);
        return eventOrganizer;
    }

    private String getCategoryNm(Event event, String categoryNm) {
        for (OerCategoryResponse x : getAll()) {
            if (x.getId().equals(event.getCategoryId())){
                categoryNm = x.getName();
            }
        }
        return categoryNm;
    }

    private String getSemesterNm(Event event, String semesterNm) {
        for (OerSemesterResponse x : getSemesters()) {
            if (x.getId().equals(event.getSemesterId())){
                semesterNm = x.getName();
            }
        }
        return semesterNm;
    }

    private List<EventMajor> getEventMajors(OerCreateEventRequest request, Event event, StringBuilder messageLog) {
        List<EventMajor> list = new ArrayList<>();
        for (int i = 0; i < request.getListMajor().length; i++) {
            EventMajor eventMajor = new EventMajor();
            eventMajor.setEventId(event.getId());
            eventMajor.setMajorId(request.getListMajor()[i]);
            for (OerMajorResponse x: getMajors()) {
                if (x.getId().equals(request.getListMajor()[i])){
                    messageLog.append(x.getName() + ConstantLogMessage.WHITE_SPACE);
                }
            }
            list.add(eventMajor);
        }
        return list;
    }

    private List<EventObject> getEventObjects(OerCreateEventRequest request, Event event, StringBuilder messageLog) {
        List<EventObject> list = new ArrayList<>();
        for (int i = 0; i < request.getListObject().length; i++) {
            EventObject eventObject = new EventObject();
            eventObject.setEventId(event.getId());
            eventObject.setObjectId(request.getListObject()[i]);
            for (OerObjectResponse x: getObjects()) {
                if (x.getId().equals(request.getListObject()[i])){
                    messageLog.append(x.getName() + ConstantLogMessage.WHITE_SPACE);
                }
            }
            list.add(eventObject);
        }
        return list;
    }

    private void newEventDTO(OerCreateEventRequest request, Event newEvent) {
        if (!(request.getStartTime() == null) && !(request.getEndTime() == null)) {
            try {
                Date startTime = new Date(request.getStartTime() * 1000L);
                Date endTime = new Date(request.getEndTime() * 1000L);
                if (startTime.compareTo(new Date()) < 0) {
                    throw new RestApiException(Message.INVALID_START_TIME); // Ngày bắt đầu không hợp lệ
                }
                if (endTime.compareTo(startTime) < 0
                        || endTime.compareTo(startTime) == 0) {
                    throw new RestApiException(Message.INVALID_END_TIME); // Ngày kết thúc không hợp lệ
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RestApiException(Message.INVALID_START_TIME); // Ngày BD hoặc KT không hợp lệ
            }
        }
        newEvent.setCategoryId(request.getIdCategory().isEmpty() ? null : request.getIdCategory());
        newEvent.setDescription(request.getDescription());
        newEvent.setSemesterId(request.getIdSemester().isEmpty() ? null : request.getIdSemester());
        newEvent.setName(request.getName());
        newEvent.setStatus(EventStatus.SCHEDULED_HELD);
        newEvent.setIsAttendance(false);
        newEvent.setExpectedParticipants(request.getExpectedParticipants() == null || request.getExpectedParticipants() == 0  ? null : request.getExpectedParticipants());
        newEvent.setEventType(request.getEventType() == null ? null : request.getEventType());
        newEvent.setBlockNumber(request.getBlockNumber());
        newEvent.setStartTime(request.getStartTime());
        newEvent.setEndTime(request.getEndTime());
        newEvent.setIsHireDesignBanner(false);
        newEvent.setIsHireDesignBg(false);
        newEvent.setIsHireDesignStandee(false);
        newEvent.setIsHireLocation(false);
        newEvent.setIsOpenRegistration(false);
        newEvent.setSubjectCode(session.getCurrentSubjectCode());
        newEvent.setTrainingFacilityCode(session.getCurrentTrainingFacilityCode());
    }


    /**
     * @deprecated
     * */
    @Override
    @Synchronized
    public EventLocation create(@Valid OerCreateEventLocationRequest request) {

//        return eventLocationRepository.save();
        return null;
    }

    /**
     * @deprecated
     * */
    @Override
    @Synchronized
    public void uploadFile(String idEvent, MultipartFile fileBackground, MultipartFile fileThumbnail) { //event register
        try {
            Path currentPath = Paths.get("");
            String parentPath = currentPath.toAbsolutePath().toString().substring(0, currentPath.toAbsolutePath().toString().lastIndexOf("\\"));
            String pathFile = parentPath + "/FE/assets/images/images_events/";
            String folderName = idEvent;
            String absoluteFilePathFolder = pathFile + folderName;
             //Create file folder event
            File folder = new File(absoluteFilePathFolder);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            String absoluteFilePath = absoluteFilePathFolder + "/";
             //Create file thumbnail
            if (fileThumbnail != null) {
                String fileNameThumbnail = fileThumbnail.getOriginalFilename();
                String filePathThumbnail = absoluteFilePath + fileNameThumbnail;
                fileThumbnail.transferTo(new File(filePathThumbnail));
            }
             //Create file background
            if (fileBackground != null) {
                String fileNameBackground = fileBackground.getOriginalFilename();
                String filePathBackground = absoluteFilePath + fileNameBackground;
                fileBackground.transferTo(new File(filePathBackground));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param:
     * @return: OerCategoryResponse
     * */
    @Override
    public List<OerCategoryResponse> getAll() {
        return categoryRepository.getAll();
    }

    /**
     * @param:
     * @return: OerSemesterResponse
     * */
    @Override
    public List<OerSemesterResponse> getSemesters() {
        return semesterRepository.getAll();
    }

    /**
     * @param:
     * @return: OerObjectResponse
     * */
    @Override
    public List<OerObjectResponse> getObjects() {
        return objectRepository.getAll();
    }

    /**
     * @param:
     * @return: OerMajorResponse
     * */
    @Override
    public List<OerMajorResponse> getMajors() {
        return majorRepository.getAll();
    }

    @Override
    public List<OerDepartmentResponse> getDepartments() {
        return departmentRepository.getAll();
    }

    @Override
    public List<OerDepartmentCampusResponse> getDepartmentsByCampus() {
        return departmentCampusRepository.getAllDepartmentsByCampus(session.getData());
    }

    @Override
    public List<OerMajorCampusResponse> getMajorsCampus() {
        return majorCampusRepository.getAllMajorCampus();
    }

    /**
     * @param: OerCreateCategoryRequest
     * @return: Category
     * */
    @Override
    @Synchronized
    public Category create(@Valid OerCreateCategoryRequest request) {
        String regexTrim = "\\s+";
        request.setName(request.getName().replaceAll(regexTrim, " "));
        Category category = new Category();
        if(categoryRepository.findByCode(request.getName()) != null){
            throw new RestApiException(Message.CATEGORY_CODE_OVERLAPPED); // Mã thể loại không được trùng
        }
        category.setName(request.getName());
        return categoryRepository.save(category);
    }

}
