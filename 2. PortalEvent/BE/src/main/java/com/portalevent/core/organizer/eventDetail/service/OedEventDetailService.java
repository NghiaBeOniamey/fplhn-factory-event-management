package com.portalevent.core.organizer.eventDetail.service;

import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaEventApproveRequest;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.organizer.eventDetail.model.request.OedAddEventLocationRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedAttendanceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateAgendaRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateEventOrganizerRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateImportEventOrganizerRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateResourceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedDeleteCommentRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedDeleteEventOrganizerRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedEventCloseRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedEvidenceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedInvitationTimeRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedMailRequestApprovalEvent;
import com.portalevent.core.organizer.eventDetail.model.request.OedPostCommentRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedRegisterRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedReplyCommentRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedSendMailEvidenceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateAgendaRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateEventLocationRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateEventOrganizer;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateEventRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateResourceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedWaitApprovalPeriodicallyRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedAgendaItemCustom;
import com.portalevent.core.organizer.eventDetail.model.response.OedAllCommentResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedCategoryResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedDepartmentCampusResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventDetailCustom;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventEvidenceResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventLocationResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventOrganizerCustom;
import com.portalevent.core.organizer.eventDetail.model.response.OedInvitationTimeResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedMajorCampusResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedMajorResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedObjectResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedReplyCommentResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedResourceResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedSemesterResponse;
import com.portalevent.entity.AgendaItem;
import com.portalevent.entity.Event;
import com.portalevent.entity.EventLocation;
import com.portalevent.entity.EventOrganizer;
import com.portalevent.entity.Evidence;
import com.portalevent.entity.InvitationTime;
import com.portalevent.entity.Resource;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author SonPT
 */
public interface OedEventDetailService {

    List<OedCategoryResponse> getAll();

    OedEventDetailCustom detail(String id);

    Event update(@Valid OedUpdateEventRequest request);

    List<OedResourceResponse> getAllResource(String idEvent);

    Resource create(@Valid OedCreateResourceRequest request);

    Resource update(@Valid OedUpdateResourceRequest request);

    String deleteResource(String id);

    List<OedSemesterResponse> getSemesters();

    List<OedMajorResponse> getMajors();

    List<OedMajorCampusResponse> getMajorCampusByIdEvent(String idEvent);

    List<OedMajorCampusResponse> getAllMajorCampus(String idDepartment);

    List<OedDepartmentCampusResponse> getAllDepartmentCampus();

    List<OedDepartmentCampusResponse> getAllDepartmentCampusByIdEvent(String idEvent);

    List<OedEventLocationResponse> getEventLocationByIdEvent(String id);

    EventLocation addEventLocation(@Valid OedAddEventLocationRequest request);

    EventLocation updateEventLocation(@Valid OedUpdateEventLocationRequest request);

    List<OedMajorResponse> getMajorByIdEvent(String id);

    List<OedEventOrganizerCustom> getAllOrganizerByIdEvent(String idEvent);

    Boolean checkOrganizerRole(String idEvent);

    Boolean checkOrganizerRoleHasEdit(String idEvent);

    List<OedAgendaItemCustom> getAllAgendaItem(String idEvent);

    List<SimpleResponse> getAllOrganizerNotInEvent(String idEvent);

    EventOrganizer createEventOrganizer(@Valid OedCreateEventOrganizerRequest request);

    String importEventOrganizer(String idEvent,MultipartFile file);

    List<OedCreateImportEventOrganizerRequest> excelDataList(MultipartFile file);

    EventOrganizer updateEventOrganizer(@Valid OedUpdateEventOrganizer request);

    String deleteEventOrganizer(@Valid OedDeleteEventOrganizerRequest request);

    AgendaItem createAgenda(@Valid OedCreateAgendaRequest request);

    AgendaItem updateAgenda(@Valid OedUpdateAgendaRequest request);

    String deleteAgenda(String id);

    Event updateStatusEventByIdEvent(String id);

    void sendEmailApprovalEvent(OedMailRequestApprovalEvent request);

    String deleteEventLocationByIdEvent(String id);

    List<OedObjectResponse> getAllObject();

    List<OedObjectResponse> getObjectByIdEvent(String id);

    List<OedAllCommentResponse> getComment(String eventId, int pageNumber);

    OedAllCommentResponse postComment(OedPostCommentRequest req);

    Boolean deleteComment(OedDeleteCommentRequest req);

    OedReplyCommentResponse replyComment(OedReplyCommentRequest req);

    ResponseObject closeEvent(OedEventCloseRequest req);

    List<AgendaItem> saveListAgenda(List<OedUpdateAgendaRequest> requests);

    String uploadImage(MultipartFile file, int type, String id);

    boolean deleteImage(String id);

    List<OedEventEvidenceResponse> getAllEventEvidence(String idEvent);

    String sendMailEvidence(OedSendMailEvidenceRequest request);

    Evidence createEvidence(@Valid OedEvidenceRequest request);

    Evidence updateEvidence(String id, @Valid OedEvidenceRequest request);

    String deleteEvidence(String id);

    Boolean openOrCloseRegister(@Valid OedRegisterRequest req);

    Boolean openOrCloseAttendance(@Valid OedAttendanceRequest req);

    List<Evidence> addEvidence(List<MultipartFile> files, String idEvent);

    ResponseObject updateNumberParticipant(int numberParticipant, String idEvent);

    String approvePeriodicEvents(@Valid OedWaitApprovalPeriodicallyRequest req);

    Event eventReorganization(String idEvent, List<Integer> value);

    String sendMailResource(String idEvent);

    List<InvitationTime> saveListInvitationTime(List<OedInvitationTimeRequest> requests);

    List<OedInvitationTimeResponse> getAllInvitationTime(String idEvent);

    String deleteInvitationTime(String id);

    void sendEmailUpdateEventWhenApproved(OedMailRequestApprovalEvent request);

//    Boolean sendConversionRequest(@Valid OedSendConversionRequest request);

//    List<HoneyCategoryResponse> getHoneyCategory();

    ResponseObject approvalEvent(AewaEventApproveRequest req);
}
