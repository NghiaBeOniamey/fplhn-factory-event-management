package com.portalevent.core.organizer.mutipleregister.model.response;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import com.sun.mail.imap.protocol.BODY;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */

@Projection(types = {Event.class})
public interface OmrEventScheduleResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.isOwnEvent}")
    Byte getIsOwnEvent();

}
