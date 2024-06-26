package com.portalevent.core.admin.eventdetailmanagement.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdminerAllCommentResponse {

    String id;

    String userId;

    String email;

    String lastModifiedDate;

    String comment;

    String avatar;

    List<AdminerReplyCommentResponse> listReply;

    Integer totalPages;

    Integer currentPage;

    Boolean isReply;

    public void addElementToListReply(AdminerReplyCommentResponse replyCommentResponse) {
        if (this.listReply == null) {
            this.listReply = new ArrayList<>();
        }
        this.listReply.add(replyCommentResponse);
    }

}
