package com.portalevent.core.adminho.eventclosedmanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHOecEventCloseRequest extends PageableRequest {

    private String name;

    private String category;

    private String object;

    private String major;

    private String semester;

}
