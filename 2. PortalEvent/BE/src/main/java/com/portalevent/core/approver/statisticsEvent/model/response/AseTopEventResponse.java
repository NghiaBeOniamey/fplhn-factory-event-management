package com.portalevent.core.approver.statisticsEvent.model.response;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author HoangDV
 */

@Projection(types = {Event.class})
public interface AseTopEventResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.numberParticipants}")
    Integer getNumberParticipant();

}
