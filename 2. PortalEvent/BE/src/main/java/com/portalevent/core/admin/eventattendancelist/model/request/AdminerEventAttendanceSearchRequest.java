package com.portalevent.core.admin.eventattendancelist.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AdminerEventAttendanceSearchRequest extends PageableRequest {

    private String idEvent;

    private String email;

    String participantCode;

    String participantName;

    Long startTimeSearch;

    Long endTimeSearch;

}
