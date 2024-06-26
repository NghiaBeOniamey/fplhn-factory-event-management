package com.portalevent.core.organizer.statisticsEvent.model.response;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author HoangDV
 */
@Projection(types = {Event.class})
public interface OseTopEventResponse extends IsIdentified {
    @Value("#{target.name}")
    String getName();

    @Value("#{target.number_participants}")
    Integer getNumberParticipant();

    @Value("#{target.event_role}")
    Integer getRole();
}
