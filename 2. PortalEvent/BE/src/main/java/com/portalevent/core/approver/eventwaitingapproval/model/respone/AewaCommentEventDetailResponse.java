package com.portalevent.core.approver.eventwaitingapproval.model.respone;

import com.portalevent.entity.Comment;
import com.portalevent.entity.Event;
import com.portalevent.entity.Participant;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Comment.class})
public interface AewaCommentEventDetailResponse extends IsIdentified {
    @Value("#{target.participant_id}")
    String getParticipantId();

    @Value("#{target.email}")
    String getEmail();

    @Value("#{target.last_modified_date}")
    Long getLastModifiedDate();

    @Value("#{target.comment}")
    String getComment();

    @Value("#{target.avatar}")
    String getAvatar();

    @Value("#{target.reply_id}")
    String getReplyId();
}
