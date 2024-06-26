package com.portalevent.core.participant.home.model.request;

import com.portalevent.infrastructure.constant.EntityProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author SonPT
 */

@Getter
@Setter
public class PhReplyCommentRequest {

    @NotBlank
    @NotEmpty
    private String eventId;
//
//    @NotBlank
//    @NotEmpty
//    private String participantId;

    @NotEmpty
    @Size(max = EntityProperties.LENGTH_COMMENT)
    private String comment;

    @NotBlank
    @NotEmpty
    private String replyId;

}
