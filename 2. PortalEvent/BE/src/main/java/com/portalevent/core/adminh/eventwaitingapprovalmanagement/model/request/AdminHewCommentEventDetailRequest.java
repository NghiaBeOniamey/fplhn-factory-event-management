package com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHewCommentEventDetailRequest extends PageableRequest {

    private String idEvent;

}
