package com.portalevent.core.organizer.hireDesignList.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Search event hire design
 */

@Getter
@Setter
@ToString
public class OhdlSearchEventRequest extends PageableRequest {

    private Long startTimeLong;

    private Long endTimeLong;

    private String formality;

    private String idOrganizer;

    private String idMajor;

}
