package com.portalevent.core.admin.eventdetailmanagement.service;

import com.portalevent.core.admin.eventdetailmanagement.model.request.AdminerDeleteCommentRequest;
import com.portalevent.core.admin.eventdetailmanagement.model.request.AdminerPostCommentRequest;
import com.portalevent.core.admin.eventdetailmanagement.model.request.AdminerReplyCommentRequest;
import com.portalevent.core.admin.eventdetailmanagement.model.response.*;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerEventApproveRequest;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.SimpleResponse;

import java.util.List;

public interface AdminerEventDetailService {

    AdminerEventDetailApprovedCustom getDetailEventApproved(String id);

    List<AdminerAllCommentResponse> getComment(String eventId, int pageNumber);

    AdminerAllCommentResponse postComment(AdminerPostCommentRequest req);

    Boolean deleteComment(AdminerDeleteCommentRequest req);

    List<AdminerAgendaItemCustom> getListAgendaItemByEventId(String eventId);

    AdminerReplyCommentResponse replyComment(AdminerReplyCommentRequest req);

    List<AdminerResourceEventResponce> getResourcesByEventId(String eventId);

    List<AdminerLocationEventResponse> getLocationByEventId(String idevent);

    List<AdminerObjectEventResponse> getObjectByEventId(String idevent);

    List<AdminerMajorResponse> getMajorByIdEvent(String id);

    List<AdminerEvidenceResponse> getEvidenceByIdEvent(String idEvent);

    Boolean approverPeriodicEvent(String idEvent);

    Boolean noApproverPeriodicEvent(String idEvent, String reason);

    ResponseObject approvalEvent(AdminerEventApproveRequest req);

    List<SimpleResponse> getListOrganizerByIdEvent(String idEvent);

    String getNameEventsInTime(String id, Long startTime, Long endTime);

}
