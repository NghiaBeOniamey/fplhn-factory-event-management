package com.portalevent.core.participant.home.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author SonPT
 */

@Getter
@Setter
public class PhDeleteCommentRequest {

    @NotBlank
    @NotEmpty
    private String commentId;

    private String idEvent;

}
