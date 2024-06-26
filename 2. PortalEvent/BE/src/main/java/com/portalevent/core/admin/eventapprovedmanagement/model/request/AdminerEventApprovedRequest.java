package com.portalevent.core.admin.eventapprovedmanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminerEventApprovedRequest extends PageableRequest {

    private String name;

    private String categoryId;

    private Long startTime;

    private Long endTime;

    private String status;

}
