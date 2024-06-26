package com.portalevent.core.participant.home.model.request;

import com.portalevent.infrastructure.constant.EntityProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhRegisterEventRequest {

    private String email;

    @Size(max = EntityProperties.LENGTH_NAME)
    private String className;

    @Size(max = EntityProperties.LENGTH_QUESTION)
    private String question;

    @Size(max = EntityProperties.LENGTH_NAME)
    private String lecturerName;

    @NotBlank
    @NotEmpty
    private String eventId;

}
