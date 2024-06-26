package com.portalevent.core.organizer.mutipleregister.service.impl;

import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.organizer.mutipleregister.model.request.OmrFastRegisterRequest;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrEventDetailResponse;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrEventScheduleResponse;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrInfomationResponse;
import com.portalevent.core.organizer.mutipleregister.repository.OmrCategoryRepository;
import com.portalevent.core.organizer.mutipleregister.repository.OmrEventMajorRepository;
import com.portalevent.core.organizer.mutipleregister.repository.OmrEventObjectRepository;
import com.portalevent.core.organizer.mutipleregister.repository.OmrEventOrganizerRepository;
import com.portalevent.core.organizer.mutipleregister.repository.OmrEventRepository;
import com.portalevent.core.organizer.mutipleregister.repository.OmrMajorRepository;
import com.portalevent.core.organizer.mutipleregister.repository.OmrObjectRepository;
import com.portalevent.core.organizer.mutipleregister.repository.OmrSemesterRepository;
import com.portalevent.core.organizer.mutipleregister.service.OmrMultipleRegisterService;
import com.portalevent.entity.Event;
import com.portalevent.entity.EventMajor;
import com.portalevent.entity.EventObject;
import com.portalevent.entity.EventOrganizer;
import com.portalevent.infrastructure.constant.EventRole;
import com.portalevent.infrastructure.constant.EventStatus;
import com.portalevent.infrastructure.constant.EventType;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.CallApiIdentity;
import com.portalevent.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author SonPT
 */

@Service
public class OmrMultipleRegisterServiceImpl implements OmrMultipleRegisterService {

    private final OmrEventRepository eventRepository;

    private final OmrEventOrganizerRepository eventOrganizerRepository;

    private final OmrCategoryRepository categoryRepository;

    private final OmrMajorRepository majorRepository;

    private final OmrObjectRepository objectRepository;

    private final OmrSemesterRepository semesterRepository;

    private final OmrEventObjectRepository eventObjectRepository;

    private final OmrEventMajorRepository eventMajorRepository;

    private final PortalEventsSession session;

    private final CallApiIdentity callApiIdentity;

    private final LoggerUtil loggerUtil;

    public OmrMultipleRegisterServiceImpl(OmrEventRepository eventRepository, OmrEventOrganizerRepository eventOrganizerRepository, OmrCategoryRepository categoryRepository, OmrMajorRepository majorRepository, OmrObjectRepository objectRepository, OmrSemesterRepository semesterRepository, OmrEventObjectRepository eventObjectRepository, OmrEventMajorRepository eventMajorRepository, PortalEventsSession session, CallApiIdentity callApiIdentity, LoggerUtil loggerUtil) {
        this.eventRepository = eventRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.categoryRepository = categoryRepository;
        this.majorRepository = majorRepository;
        this.objectRepository = objectRepository;
        this.semesterRepository = semesterRepository;
        this.eventObjectRepository = eventObjectRepository;
        this.eventMajorRepository = eventMajorRepository;
        this.session = session;
        this.callApiIdentity = callApiIdentity;
        this.loggerUtil = loggerUtil;
    }

    @Override
    public List<OmrEventScheduleResponse> getAllForCalendar() {
        return eventRepository.getAllForCalendar(session.getCurrentIdUser(), session.getData());
    }

    @Override
    public OmrInfomationResponse getAllInfo() {
        OmrInfomationResponse response = new OmrInfomationResponse();
        response.setListSemester(semesterRepository.getAllForDisplay());
        response.setListMajor(majorRepository.getAllForDisplay());
        response.setListCategory(categoryRepository.getAllForDisplay());
        response.setListObject(objectRepository.getAllForDisplay());
        return response;
    }

    @Transactional
    @Override
    public List<OmrEventScheduleResponse> multipleRegister(List<OmrFastRegisterRequest> newEvents) {
        Map<String, String> lstobject = new HashMap<>();
        objectRepository.findAll().forEach(item -> {
            lstobject.put(item.getId(), item.getName());
        });
        Map<String, String> lstMajor = new HashMap<>();
        majorRepository.findAll().forEach(item -> {
            lstMajor.put(item.getId(), item.getName());
        });
        Map<String, String> lstCategory = new HashMap<>();
        categoryRepository.findAll().forEach(item -> {
            lstCategory.put(item.getId(), item.getName());
        });
        Map<String, String> lstSemester = new HashMap<>();
        semesterRepository.findAll().forEach(item -> {
            lstSemester.put(item.getId(), item.getName());
        });
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        List<EventOrganizer> eventOrganizers = new ArrayList<>();
        List<EventObject> lstEventObject = new ArrayList<>();
        List<EventMajor> lstEventMajor = new ArrayList<>();
        for (OmrFastRegisterRequest item: newEvents) {
            if (item.getName().trim().isEmpty()) {
                throw new RestApiException(Message.EVENT_NAME_IS_REQUIRED);
            }
            Long startTime = null;
            Long endTime = null;
            if (item.getStartTime() != null && item.getEndTime() != null) {
                try {
                    startTime = item.getStartTime();
                    endTime = item.getEndTime();
                    if (endTime <= startTime) {
                        throw new RestApiException(Message.INVALID_END_TIME); // Ngày kết thúc không hợp lệ
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RestApiException(Message.INVALID_START_TIME); // Ngày BD hoặc KT không hợp lệ
                }
            }
            StringBuilder majorName = new StringBuilder("");
            if (item.getMajorId() != null) {
                item.getMajorId().forEach(majorId -> {
                    if (!lstMajor.containsKey(majorId)) {
                        throw new RestApiException(Message.MAJOR_NOT_EXIST);
                    } else {
                        majorName.append(lstMajor.get(majorId)).append(", ");
                        EventMajor eventMajor = new EventMajor();
                        eventMajor.setMajorId(majorId);
                        lstEventMajor.add(eventMajor);
                    }
                });
            }
            StringBuilder objectsName = new StringBuilder("");
            if (item.getObjectId() != null) {
                item.getObjectId().forEach(objectId -> {
                    if (!lstobject.containsKey(objectId)) {
                        throw new RestApiException(Message.OBJECT_NOT_EXIST);
                    } else {
                        objectsName.append(lstobject.get(objectId)).append(", ");
                        EventObject eventObject = new EventObject();
                        eventObject.setObjectId(objectId);
                        lstEventObject.add(eventObject);
                    }
                });
            }
            if (!lstCategory.containsKey(item.getCategoryId()) && item.getCategoryId() != null) {
                throw new RestApiException(Message.CATEGORY_NOT_EXIST);
            }
            if (!lstSemester.containsKey(item.getSemesterId()) && item.getSemesterId() != null) {
                throw new RestApiException(Message.SEMESTER_NOT_EXISTS);
            }
            Event newEvent = new Event();
            newEvent.setName(item.getName());
            newEvent.setStartTime(item.getStartTime());
            newEvent.setEndTime(item.getEndTime());
            newEvent.setExpectedParticipants(item.getExpectedParticipants());
            newEvent.setCategoryId(item.getCategoryId());
            if (item.getEventType() != null) {
                newEvent.setEventType(item.getEventType() == 0 ? EventType.STUDENT_EVENT : item.getEventType() == 1 ?
                        EventType.LECTURER_EVENT : item.getEventType() == 2 ? EventType.COMBINED_EVNT : EventType.STUDENT_EVENT);
            }
            newEvent.setStatus(EventStatus.SCHEDULED_HELD);
            newEvent.setSemesterId(item.getSemesterId());
            newEvent.setBlockNumber(item.getBlockNumber());
            newEvent.setTrainingFacilityCode(session.getCurrentTrainingFacilityCode());
            newEvent.setSubjectCode(session.getCurrentSubjectCode());
            newEvent = eventRepository.save(newEvent);

            EventOrganizer eventOrganizer = new EventOrganizer();
            eventOrganizer.setEventId(newEvent.getId());
            eventOrganizer.setEventRole(EventRole.HOST);
            eventOrganizer.setOrganizerId(session.getCurrentUserCode());
            eventOrganizer.setOrganizerCode(session.getCurrentUserCode());
            eventOrganizers.add(eventOrganizer);
            for (EventObject eventObject: lstEventObject) {
                eventObject.setEventId(newEvent.getId());
            }
            for (EventMajor eventMajor: lstEventMajor) {
                eventMajor.setEventId(newEvent.getId());
            }
            StringBuilder messageLog = new StringBuilder("Đăng ký sự kiện `" + newEvent.getName() + "`: thời gian bắt đầu" + sdf.format(new Date(newEvent.getStartTime())));
            messageLog.append(" | thời gian kết thúc: " + sdf.format(new Date(newEvent.getEndTime())));
            messageLog.append(" | thể loại: ");
            messageLog.append(newEvent.getCategoryId() != null ? lstCategory.get(newEvent.getCategoryId()) : "chưa chọn");
            messageLog.append(" | số người tham gia dự kiến: ");
            messageLog.append(newEvent.getExpectedParticipants() != null ? newEvent.getExpectedParticipants() : " chưa xác định");
            messageLog.append(" | kiểu sự kiện: ");
            if (newEvent.getEventType() != null) {
                messageLog.append(item.getEventType() == 0 ? " Sự kiện dành cho sinh viên" : item.getEventType() == 1 ?
                        "Sự kiện dành cho Giảng viên" : "Sự kiện dành cho giảng viên và sinh viên");
            } else {
                messageLog.append("Chưa chọn");
            }
            messageLog.append(" | bộ môn: ");
            if (!majorName.isEmpty()) {
                messageLog.append(majorName);
            } else {
                messageLog.append("Chưa chọn");
            }
            messageLog.append(" | Đối tượng: ");
            if (!objectsName.isEmpty()) {
                messageLog.append(objectsName);
            } else {
                messageLog.append("Chưa chọn");
            }
            loggerUtil.sendLog(messageLog.toString(), newEvent.getId());
        }
        if (!eventOrganizers.isEmpty()) {
            //save event organizer
            eventOrganizerRepository.saveAll(eventOrganizers);
        }
        if (!lstEventMajor.isEmpty()) {
            eventMajorRepository.saveAll(lstEventMajor);
        }
        if (!lstEventObject.isEmpty()) {
            eventObjectRepository.saveAll(lstEventObject);
        }
        return getAllForCalendar();
    }

    @Override
    public OmrEventDetailResponse getDetail(String id) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        List<OmrEventDetailResponse.EventResponse> event = eventRepository.getDetailEvent(id );
        if (event.isEmpty()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        OmrEventDetailResponse response = new OmrEventDetailResponse();
        response.setId(event.get(0).getId());
        response.setName(event.get(0).getName());
        Short status = event.get(0).getStatus();
        response.setStatus(status == 0 ? "Hủy" : status == 1 ? "Dự kiến tổ chức" : status == 2 ? "Cần sửa" : status == 3 ? "Chờ phê duyệt" : status == 4 ? "Đã phê duyệt" : "Đã tổ chức");
        response.setTime((event.get(0).getStartTime() == null ? "Chưa đặt thời gian BĐ" :sdf.format(new Date(event.get(0).getStartTime())))
                + " - " + (event.get(0).getEndTime() == null ? "Chưa đặt thời gian KT" : sdf.format(new Date(event.get(0).getEndTime()))));
        response.setCategoryName(event.get(0).getCategoryName() == null ? "Chưa đặt" : event.get(0).getCategoryName());
        response.setSemesterName(event.get(0).getSemesterName() == null ? "Chưa đặt" : event.get(0).getSemesterName());
        response.setBlockName(event.get(0).getBlockNumber() == null ? "Chưa đặt" : event.get(0).getBlockNumber());
        response.setExpectedParticipants(event.get(0).getExpectedParticipants() == null ? 0 : event.get(0).getExpectedParticipants());
        response.setEventType(event.get(0).getEventType() == null ? "Chưa đặt" : event.get(0).getEventType() == 0 ? "Sự kiện dành cho sinh viên" :
                event.get(0).getEventType() == 1 ? "Sự kiện dành cho giảng viên" :
                        event.get(0).getEventType() == 2 ? "Sự kiện dành cho cả giảng viên và sinh viên" : "");
        List<String> listMajorName = new ArrayList<>();
        List<String> listObjectName = new ArrayList<>();
        List<String> listOrganizerId = new ArrayList<>();
        event.forEach(item -> {
            if (item.getObjectName() != null) {
                if (!listObjectName.contains(item.getObjectName())) {
                    listObjectName.add(item.getObjectName());
                }
            }
            if (item.getMajorName() != null) {
                if (!listMajorName.contains(item.getMajorName())) {
                    listMajorName.add(item.getMajorName());
                }
            }
            if (item.getOranizerId() != null) {
                if (!listOrganizerId.contains(item.getOranizerId())) {
                    listOrganizerId.add(item.getOranizerId());
                }
            }
        });
        String majorName = "";
        if (!listMajorName.isEmpty()) {
            for (String item: listMajorName) {
                majorName += item + ", ";
            };
        } else {
        	majorName += "Chưa đặt";
        }
        response.setMajors(majorName);
        String objectName = "";
        if (!listObjectName.isEmpty()) {
            for (String item: listObjectName) {
                objectName += item + ", ";
            };
            response.setObjects(objectName);
        } else {
            objectName += "Chưa đặt";
        }
        response.setObjects(objectName);
        StringBuilder organizerAccount = new StringBuilder();
        if (!listOrganizerId.isEmpty()) {
            List<SimpleResponse> listOrganizer = callApiIdentity.handleCallApiGetListUserByListId(listOrganizerId);
            if (listOrganizer != null) {
                for (SimpleResponse item : listOrganizer) {
                    organizerAccount.append(item.getUserName()).append(", ");
                }
            } else {
                organizerAccount.append("Chưa có");
            }
        } else {
            organizerAccount.append("Chưa có");
        }
        response.setOrganizers(organizerAccount.toString());
        return response;
    }

}
