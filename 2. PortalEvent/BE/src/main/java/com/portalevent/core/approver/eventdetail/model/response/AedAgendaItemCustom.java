package com.portalevent.core.approver.eventdetail.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AedAgendaItemCustom {
    private String id;
    private Integer index;
    private String startTime;
    private String endTime;
    private String description;
    private String organizerId;
    private String organizerName;
    private String organizerUsername;
}
