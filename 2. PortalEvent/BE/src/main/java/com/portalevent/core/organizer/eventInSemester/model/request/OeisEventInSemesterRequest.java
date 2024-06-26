package com.portalevent.core.organizer.eventInSemester.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OeisEventInSemesterRequest extends PageableRequest {

    private String name;

    private String semester;

    private String organizer;

}
