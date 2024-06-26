package com.portalevent.core.organizer.eventDetail.model.request;

import com.portalevent.entity.base.PrimaryEntity;
import com.portalevent.infrastructure.constant.EventRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OedUpdateEventOrganizer {

    @NotNull
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String organizerId;

    @NotNull
    @NotBlank
    private String organizerUser;

    @NotNull
    private Integer eventRole;

    @NotNull
    @NotBlank
    private String eventId;

    @NotNull
    private Double meetingHour;

}
