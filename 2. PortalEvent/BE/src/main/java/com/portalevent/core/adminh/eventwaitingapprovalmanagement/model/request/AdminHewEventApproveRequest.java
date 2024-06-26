package com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHewEventApproveRequest {

    private String id;

    private String reason;

    private Short status;

}
