package com.portalevent.core.adminho.eventdetailmanagement.service;

import com.portalevent.core.adminho.eventdetailmanagement.model.request.AdminHOedDeleteCommentRequest;
import com.portalevent.core.adminho.eventdetailmanagement.model.request.AdminHOedPostCommentRequest;
import com.portalevent.core.adminho.eventdetailmanagement.model.request.AdminHOedReplyCommentRequest;
import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOedAgendaItemCustom;
import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOedAllCommentResponse;
import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOedEventDetailApprovedCustom;
import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOedLocationEventResponse;
import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOedMajorResponse;
import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOedObjectEventResponse;
import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOedReplyCommentResponse;
import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOedResourceEventResponse;
import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOewEvidenceResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOewEventApproveRequest;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.SimpleResponse;

import java.util.List;

public interface AdminHOedEventDetailService {

    AdminHOedEventDetailApprovedCustom getDetailEventApproved(String id);

    List<AdminHOedAllCommentResponse> getComment(String eventId, int pageNumber);

    AdminHOedAllCommentResponse postComment(AdminHOedPostCommentRequest req);

    Boolean deleteComment(AdminHOedDeleteCommentRequest req);

    List<AdminHOedAgendaItemCustom> getListAgendaItemByEventId(String eventId);

    AdminHOedReplyCommentResponse replyComment(AdminHOedReplyCommentRequest req);

    List<AdminHOedResourceEventResponse> getResourcesByEventId(String eventId);

    List<AdminHOedLocationEventResponse> getLocationByEventId(String idevent);

    List<AdminHOedObjectEventResponse> getObjectByEventId(String idevent);

    List<AdminHOedMajorResponse> getMajorByIdEvent(String id);

    List<AdminHOewEvidenceResponse> getEvidenceByIdEvent(String idEvent);

    Boolean approverPeriodicEvent(String idEvent);

    Boolean noApproverPeriodicEvent(String idEvent, String reason);

    ResponseObject approvalEvent(AdminHOewEventApproveRequest req);

    List<SimpleResponse> getListOrganizerByIdEvent(String idEvent);

    String getNameEventsInTime(String id, Long startTime, Long endTime);

}
