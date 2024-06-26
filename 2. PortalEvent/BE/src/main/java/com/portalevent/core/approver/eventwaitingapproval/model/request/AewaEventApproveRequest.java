package com.portalevent.core.approver.eventwaitingapproval.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AewaEventApproveRequest {

    private String id;

    private String reason;

    private Short status;

}
