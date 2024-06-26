package com.portalevent.core.organizer.statisticsEvent.model.response;

import com.portalevent.entity.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author HoangDV
 */
@Projection(types = {Event.class})
public interface OseEventInSemesterResponse {
    @Value("#{target.status}")
    Integer getStatus();

    @Value("#{target.quantity}")
    Integer getQuantity();
}
