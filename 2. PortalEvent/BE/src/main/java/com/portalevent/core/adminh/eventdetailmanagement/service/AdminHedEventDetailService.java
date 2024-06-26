package com.portalevent.core.adminh.eventdetailmanagement.service;

import com.portalevent.core.adminh.eventdetailmanagement.model.request.AdminHedDeleteCommentRequest;
import com.portalevent.core.adminh.eventdetailmanagement.model.request.AdminHedPostCommentRequest;
import com.portalevent.core.adminh.eventdetailmanagement.model.request.AdminHedReplyCommentRequest;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedAgendaItemCustom;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedAllCommentResponse;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedEventDetailApprovedCustom;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedLocationEventResponse;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedMajorResponse;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedObjectEventResponse;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedReplyCommentResponse;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedResourceEventResponse;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHewEvidenceResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHewEventApproveRequest;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.SimpleResponse;

import java.util.List;

public interface AdminHedEventDetailService {

    AdminHedEventDetailApprovedCustom getDetailEventApproved(String id);

    List<AdminHedAllCommentResponse> getComment(String eventId, int pageNumber);

    AdminHedAllCommentResponse postComment(AdminHedPostCommentRequest req);

    Boolean deleteComment(AdminHedDeleteCommentRequest req);

    List<AdminHedAgendaItemCustom> getListAgendaItemByEventId(String eventId);

    AdminHedReplyCommentResponse replyComment(AdminHedReplyCommentRequest req);

    List<AdminHedResourceEventResponse> getResourcesByEventId(String eventId);

    List<AdminHedLocationEventResponse> getLocationByEventId(String idevent);

    List<AdminHedObjectEventResponse> getObjectByEventId(String idevent);

    List<AdminHedMajorResponse> getMajorByIdEvent(String id);

    List<AdminHewEvidenceResponse> getEvidenceByIdEvent(String idEvent);

    Boolean approverPeriodicEvent(String idEvent);

    Boolean noApproverPeriodicEvent(String idEvent, String reason);

    ResponseObject approvalEvent(AdminHewEventApproveRequest req);

    List<SimpleResponse> getListOrganizerByIdEvent(String idEvent);

    String getNameEventsInTime(String id, Long startTime, Long endTime);

}
