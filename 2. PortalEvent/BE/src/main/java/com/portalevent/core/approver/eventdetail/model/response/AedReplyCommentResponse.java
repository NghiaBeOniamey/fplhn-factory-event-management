package com.portalevent.core.approver.eventdetail.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AedReplyCommentResponse {

    String id;

    String userId;

    String email;

    String lastModifiedDate;

    String comment;

    String avatar;

    String replyId;

}
