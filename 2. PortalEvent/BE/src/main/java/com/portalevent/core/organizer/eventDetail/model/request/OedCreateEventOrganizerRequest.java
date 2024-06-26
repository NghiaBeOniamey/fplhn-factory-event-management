package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class OedCreateEventOrganizerRequest {

    @NotNull
    @NotBlank
    private String organizerId;

    @NotNull
    private Integer eventRole;

    @NotNull
    @NotBlank
    private String eventId;

    @NotNull
    @Min(value = 0)
    private Double meetingHour;
}
