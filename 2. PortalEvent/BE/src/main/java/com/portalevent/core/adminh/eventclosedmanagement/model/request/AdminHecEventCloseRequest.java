package com.portalevent.core.adminh.eventclosedmanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHecEventCloseRequest extends PageableRequest {

    private String name;

    private String category;

    private String object;

    private String major;

    private String semester;

}
