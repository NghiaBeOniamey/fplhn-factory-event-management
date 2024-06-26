package com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminerEventApproveRequest {

    private String id;

    private String reason;

    private Short status;

}
