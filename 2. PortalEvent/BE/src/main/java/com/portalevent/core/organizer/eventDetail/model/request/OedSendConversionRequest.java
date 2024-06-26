package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedSendConversionRequest {

    @NotEmpty
    @NotBlank
    private String eventId;

    @NotNull
    private Integer numberHoney;

    @NotEmpty
    @NotBlank
    private String honeyCategoryId;

}
