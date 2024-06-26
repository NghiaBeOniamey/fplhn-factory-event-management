package com.portalevent.core.participant.home.model.response;

import com.portalevent.entity.Comment;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */

@Projection(types = {Comment.class})
public interface PhCommentResponse extends IsIdentified {
    @Value("#{target.user_id}")
    String getUserId();

    @Value("#{target.last_modified_date}")
    Long getLastModifiedDate();

    @Value("#{target.comment}")
    String getComment();

    @Value("#{target.reply_id}")
    String getReplyId();

}
