package com.portalevent.core.admin.eventdetailmanagement.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminerReplyCommentResponse {

    String id;

    String userId;

    String email;

    String lastModifiedDate;

    String comment;

    String avatar;

    String replyId;

}
