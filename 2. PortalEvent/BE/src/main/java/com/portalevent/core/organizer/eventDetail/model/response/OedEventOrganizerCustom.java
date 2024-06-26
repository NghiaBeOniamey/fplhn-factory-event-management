package com.portalevent.core.organizer.eventDetail.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class OedEventOrganizerCustom {

    private String id;

    private String eventId;

    private String organizerId;

    private Integer eventRole;

    private String name;

    private String username;

    private String email;

    private Double meetingHour;
}
