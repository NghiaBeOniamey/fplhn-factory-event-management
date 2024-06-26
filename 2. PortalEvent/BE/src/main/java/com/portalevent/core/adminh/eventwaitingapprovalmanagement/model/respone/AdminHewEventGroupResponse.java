package com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Event.class)
public interface AdminHewEventGroupResponse extends IsIdentified {

    String getName();

}
