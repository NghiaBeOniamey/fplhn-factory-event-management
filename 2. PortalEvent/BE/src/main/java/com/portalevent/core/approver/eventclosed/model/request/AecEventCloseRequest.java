package com.portalevent.core.approver.eventclosed.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AecEventCloseRequest extends PageableRequest {

    private String name;

    private String category;

    private String object;

    private String major;

    private String semester;

}
