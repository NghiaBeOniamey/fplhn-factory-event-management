package com.portalevent.core.organizer.eventRegistered.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author thangncph26123
 */
@Getter
@Setter
@ToString
public class OerdFilterEventRequest {

    private String idOrganizer;

    private Short status;

    private String name;

    private String idCategory;

    private Long startTime;

    private Long endTime;

    private Short formality;

    private Short statusSort;

    private String idSemester;

    private Short isMe;

}
