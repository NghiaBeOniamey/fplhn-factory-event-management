package com.portalevent.core.organizer.eventDetail.model.response;

import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class OedAgendaItemCustom {

    private int index;

    private String id;

    private String eventId;

    private String organizerId;

    private String name;

    private String username;

    private String startTime;

    private String endTime;

    private String description;
}
