package com.portalevent.core.participant.home.service;

import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.participant.home.model.request.PhDeleteCommentRequest;
import com.portalevent.core.participant.home.model.request.PhPostCommentRequest;
import com.portalevent.core.participant.home.model.request.PhReplyCommentRequest;
import com.portalevent.core.participant.home.model.response.PhAllCommentResponse;
import com.portalevent.core.participant.home.model.response.PhReplyCommentResponse;
import com.portalevent.core.participant.home.model.response.PhDetailEventResponse;
import com.portalevent.core.participant.home.model.response.PhEventCommingUpResponse;
import com.portalevent.core.participant.home.model.response.PhEventScheduleResponse;

import java.util.List;

public interface PhHomeService {

    List<PhEventScheduleResponse> getAll();

    List<PhDetailEventResponse> eventDetail(String id);

    List<PhEventCommingUpResponse> getEventComingUp();

    String registerEvent(String eventId);

    Boolean rollCall(String eventId);

    Boolean checkRollCall(String eventId);

    List<SimpleResponse> listOrganizer();

    PhAllCommentResponse postComment(PhPostCommentRequest req);

    List<PhAllCommentResponse> getComment(String eventId, int pageNumber);

    Boolean deleteComment(PhDeleteCommentRequest req);

    Boolean checkRegisterToParticipate(String eventId);

    PhReplyCommentResponse replyComment(PhReplyCommentRequest req);
}
