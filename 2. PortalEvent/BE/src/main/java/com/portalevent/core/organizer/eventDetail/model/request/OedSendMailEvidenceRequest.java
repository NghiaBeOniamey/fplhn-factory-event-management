package com.portalevent.core.organizer.eventDetail.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedSendMailEvidenceRequest {

    private String idEvent;

    private String percentage;

    private String countParticipant;

}
