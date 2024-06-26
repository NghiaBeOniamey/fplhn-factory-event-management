package com.portalevent.core.approver.eventdetail.service;

import com.portalevent.core.approver.eventdetail.model.request.AedDeleteCommentRequest;
import com.portalevent.core.approver.eventdetail.model.request.AedPostCommentRequest;
import com.portalevent.core.approver.eventdetail.model.request.AedReplyCommentRequest;
import com.portalevent.core.approver.eventdetail.model.response.AedAgendaItemCustom;
import com.portalevent.core.approver.eventdetail.model.response.AedAllCommentResponse;
import com.portalevent.core.approver.eventdetail.model.response.AedEventDetailApprovedCustom;
import com.portalevent.core.approver.eventdetail.model.response.AedLocationEventResponse;
import com.portalevent.core.approver.eventdetail.model.response.AedMajorResponse;
import com.portalevent.core.approver.eventdetail.model.response.AedObjectEventResponse;
import com.portalevent.core.approver.eventdetail.model.response.AedReplyCommentResponse;
import com.portalevent.core.approver.eventdetail.model.response.AedResourceEventResponce;
import com.portalevent.core.approver.eventdetail.model.response.AewaEvidenceResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaEventApproveRequest;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.SimpleResponse;
import java.util.List;

public interface AedEventDetailService {

    AedEventDetailApprovedCustom getDetailEventApproved(String id);

    List<AedAllCommentResponse> getComment(String eventId, int pageNumber);

    AedAllCommentResponse postComment(AedPostCommentRequest req);

    Boolean deleteComment(AedDeleteCommentRequest req);

    List<AedAgendaItemCustom> getListAgendaItemByEventId(String eventId);

    AedReplyCommentResponse replyComment(AedReplyCommentRequest req);

    List<AedResourceEventResponce> getResourcesByEventId(String eventId);

    List<AedLocationEventResponse> getLocationByEventId(String idevent);

    List<AedObjectEventResponse> getObjectByEventId(String idevent);

    List<AedMajorResponse> getMajorByIdEvent(String id);

    List<AewaEvidenceResponse> getEvidenceByIdEvent(String idEvent);

    Boolean approverPeriodicEvent(String idEvent);

    Boolean noApproverPeriodicEvent(String idEvent, String reason);

    ResponseObject approvalEvent(AewaEventApproveRequest req);

    List<SimpleResponse> getListOrganizerByIdEvent(String idEvent);

    String getNameEventsInTime(String id, Long startTime, Long endTime);

}
