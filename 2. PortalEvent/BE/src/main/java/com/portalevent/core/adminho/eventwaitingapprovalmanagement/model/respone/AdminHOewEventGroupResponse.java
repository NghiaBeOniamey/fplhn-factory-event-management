package com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = Event.class)
public interface AdminHOewEventGroupResponse extends IsIdentified {

    String getName();

}
