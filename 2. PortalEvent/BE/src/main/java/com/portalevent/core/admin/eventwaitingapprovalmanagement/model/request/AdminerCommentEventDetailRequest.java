package com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminerCommentEventDetailRequest extends PageableRequest {

    private String idEvent;

}
