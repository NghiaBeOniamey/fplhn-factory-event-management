package com.portalevent.core.admin.eventdetailmanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminerDeleteCommentRequest {

    @NotBlank
    @NotEmpty
    private String commentId;

    @NotBlank
    @NotEmpty
    private String userId;

}
