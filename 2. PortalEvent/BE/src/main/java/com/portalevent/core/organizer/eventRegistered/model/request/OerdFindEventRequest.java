package com.portalevent.core.organizer.eventRegistered.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SonPT
 */
@Getter
@Setter
@ToString
public class OerdFindEventRequest extends PageableRequest {

    private String idOrganizer;

    private Short status;

    private String name;

    private String idCategory;

    private String startTime;

    private String endTime;

    private String formality;

    private Short statusSort;

    private String idSemester;

    private Boolean isMe;

}
