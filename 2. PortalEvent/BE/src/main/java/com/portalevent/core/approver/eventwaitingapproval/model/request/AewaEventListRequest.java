package com.portalevent.core.approver.eventwaitingapproval.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AewaEventListRequest extends PageableRequest {

    private String name;

    private String eventGroup;

    private String categoryId;

    private String majorId;

    private Long startTime;

    private Long endTime;

    private String formality;

    private String status;

}
