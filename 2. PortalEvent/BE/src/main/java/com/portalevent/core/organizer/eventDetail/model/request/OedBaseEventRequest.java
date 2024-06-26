package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

/**
 * @author SonPT
 */
@Getter
@Setter
@ToString
public class OedBaseEventRequest {

    @NotBlank
    @Length(max = 500)
    private String name;

    private String idCategory;

    private String[] idMajor;

    private String[] idObject;

    private Integer eventType;

    private Long startTime;

    private Long endTime;

    private int expectedParticipants;

    private String description;

}
