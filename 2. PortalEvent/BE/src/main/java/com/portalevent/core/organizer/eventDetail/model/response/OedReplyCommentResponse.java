package com.portalevent.core.organizer.eventDetail.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedReplyCommentResponse {

    String id;

    String userId;

    String email;

    String lastModifiedDate;

    String comment;

    String avatar;

    String replyId;

}
