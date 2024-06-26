package com.portalevent.core.adminh.eventdetailmanagement.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHedAgendaItemCustom {

    private String id;

    private Integer index;

    private String startTime;

    private String endTime;

    private String description;

    private String organizerId;

    private String organizerName;

    private String organizerUsername;

}
