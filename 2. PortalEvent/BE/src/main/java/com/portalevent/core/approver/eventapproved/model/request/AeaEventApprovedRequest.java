package com.portalevent.core.approver.eventapproved.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AeaEventApprovedRequest extends PageableRequest {

    private String name;

    private String categoryId;

    private Long startTime;

    private Long endTime;

    private String status;

}
