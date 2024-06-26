package com.portalevent.core.organizer.eventDetail.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedEventCloseRequest {

    private String idEvent;

    private String reason;

    private Short status;

}
