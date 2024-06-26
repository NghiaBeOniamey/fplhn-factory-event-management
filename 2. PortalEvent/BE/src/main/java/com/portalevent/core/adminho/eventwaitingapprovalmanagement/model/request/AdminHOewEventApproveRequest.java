package com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHOewEventApproveRequest {

    private String id;

    private String reason;

    private Short status;

}
