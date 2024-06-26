package com.portalevent.core.organizer.eventDetail.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OedAllCommentResponse {

    String id;

    String userId;

    String email;

    String lastModifiedDate;

    String comment;

    String avatar;

    List<OedReplyCommentResponse> listReply;

    Integer totalPages;

    Integer currentPage;

    Boolean isReply;

    public void addElementToListReply(OedReplyCommentResponse replyCommentResponse) {
        if (this.listReply == null) {
            this.listReply = new ArrayList<>();
        }
        this.listReply.add(replyCommentResponse);
    }
}
