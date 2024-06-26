package com.portalevent.core.adminh.eventapprovedmanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHeaEventApprovedRequest extends PageableRequest {

    private String name;

    private String categoryId;

    private Long startTime;

    private Long endTime;

    private String status;

}
