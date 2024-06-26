package com.portalevent.core.participant.home.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SonPT
 */

@Getter
@Setter
public class PhAllCommentResponse {

    String id;

    String userId;

    String name;

    String lastModifiedDate;

    String comment;

    String avatar;

    List<PhReplyCommentResponse> listReply;

    Integer totalPages;

    Integer currentPage;

    Boolean isReply;

    public void addElementToListReply(PhReplyCommentResponse replyCommentResponse) {
        if (this.listReply == null) {
            this.listReply = new ArrayList<>();
        }
        this.listReply.add(replyCommentResponse);
    }

}
