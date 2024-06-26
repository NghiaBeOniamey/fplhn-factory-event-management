package com.portalevent.core.adminho.eventdetailmanagement.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AdminHOedAllCommentResponse {

    String id;

    String userId;

    String email;

    String lastModifiedDate;

    String comment;

    String avatar;

    List<AdminHOedReplyCommentResponse> listReply;

    Integer totalPages;

    Integer currentPage;

    Boolean isReply;

    public void addElementToListReply(AdminHOedReplyCommentResponse replyCommentResponse) {
        if (this.listReply == null) {
            this.listReply = new ArrayList<>();
        }
        this.listReply.add(replyCommentResponse);
    }

}
