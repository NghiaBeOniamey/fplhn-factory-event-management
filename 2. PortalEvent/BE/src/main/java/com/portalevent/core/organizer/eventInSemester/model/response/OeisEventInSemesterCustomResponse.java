package com.portalevent.core.organizer.eventInSemester.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OeisEventInSemesterCustomResponse {
    private Integer index;
    private String id;
    private String name;
    private Integer status;
    private String category;
    private String object;
    private String formality;
    private Integer expectedParticipant;
    private Integer numberParticipant;
    private String organizer;
}
