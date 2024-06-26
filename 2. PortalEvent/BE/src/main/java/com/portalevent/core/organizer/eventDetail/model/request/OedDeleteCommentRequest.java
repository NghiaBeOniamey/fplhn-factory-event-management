package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedDeleteCommentRequest {
    @NotBlank
    @NotEmpty
    private String commentId;

//    @NotBlank
//    @NotEmpty
//    private String userId;
}
