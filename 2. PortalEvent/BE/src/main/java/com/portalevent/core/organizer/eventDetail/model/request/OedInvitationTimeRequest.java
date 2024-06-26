package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedInvitationTimeRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String eventId;

    @NotNull
    private Long time;

}
