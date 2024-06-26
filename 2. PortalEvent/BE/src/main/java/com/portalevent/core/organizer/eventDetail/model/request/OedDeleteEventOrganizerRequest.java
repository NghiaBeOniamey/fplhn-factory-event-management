package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class OedDeleteEventOrganizerRequest {

    @NotBlank
    private String id;

    @NotBlank
    private String organizerId;

    @NotBlank
    private String eventId;

    @NotBlank
    private String idUserCurrent;
}
