package com.portalevent.core.organizer.eventRegister.model.request;

import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.constant.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author SonPT
 */
@Getter
@Setter
public class OerCreateEventRequest {

    @Length(max = EntityProperties.LENGTH_ID)
    private String idCategory;

    @Length(max = EntityProperties.LENGTH_ID)
    private String idSemester;

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = EntityProperties.LENGTH_NAME)
    private String name;

    private Long startTime;

    private Long endTime;

    private Boolean blockNumber;

    private String description;

    private String[] listMajor;

    private String[] listObject;

    private EventType eventType;

    private Short expectedParticipants;


}
