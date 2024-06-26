package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedUpdateNumberParticipantRequest {

    @NotNull
    @NotBlank
    private String id;

    @NotNull
    private int numberParticipant;

}
