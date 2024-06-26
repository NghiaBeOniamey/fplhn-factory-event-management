package com.portalevent.core.admin.eventdetailmanagement.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminerAgendaItemCustom {

    private String id;

    private Integer index;

    private String startTime;

    private String endTime;

    private String description;

    private String organizerId;

    private String organizerName;

    private String organizerUsername;

}
