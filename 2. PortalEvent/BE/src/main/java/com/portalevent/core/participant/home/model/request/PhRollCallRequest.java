package com.portalevent.core.participant.home.model.request;

import com.portalevent.infrastructure.constant.EntityProperties;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhRollCallRequest {

    @NotBlank
    @NotEmpty
    private String eventId;

//    @NotEmpty
//    @NotBlank
//    private String participantId;

    @Size(max = EntityProperties.LENGTH_FEEDBACK)
    private String feedback;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Byte rate;

}
