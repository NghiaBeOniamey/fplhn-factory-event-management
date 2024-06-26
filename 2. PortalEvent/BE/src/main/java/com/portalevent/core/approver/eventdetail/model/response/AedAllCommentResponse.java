package com.portalevent.core.approver.eventdetail.model.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AedAllCommentResponse {

    String id;

    String userId;

    String email;

    String lastModifiedDate;

    String comment;

    String avatar;

    List<AedReplyCommentResponse> listReply;

    Integer totalPages;

    Integer currentPage;

    Boolean isReply;

    public void addElementToListReply(AedReplyCommentResponse replyCommentResponse) {
        if (this.listReply == null) {
            this.listReply = new ArrayList<>();
        }
        this.listReply.add(replyCommentResponse);
    }

}
