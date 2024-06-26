package com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHOewCommentEventDetailRequest extends PageableRequest {

    private String idEvent;

}
