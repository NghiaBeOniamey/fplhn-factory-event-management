package com.portalevent.core.organizer.eventDetail.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedCommentEventDetailRequest extends PageableRequest {

    private String idEvent;

}
