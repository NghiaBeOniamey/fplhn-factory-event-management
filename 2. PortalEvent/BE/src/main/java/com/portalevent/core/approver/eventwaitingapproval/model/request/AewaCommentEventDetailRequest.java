package com.portalevent.core.approver.eventwaitingapproval.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AewaCommentEventDetailRequest extends PageableRequest {

    private String idEvent;

}
