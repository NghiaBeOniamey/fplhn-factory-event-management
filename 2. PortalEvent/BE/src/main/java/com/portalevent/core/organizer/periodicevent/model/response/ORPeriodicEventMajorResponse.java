package com.portalevent.core.organizer.periodicevent.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface ORPeriodicEventMajorResponse extends IsIdentified {

    @Value("#{target.major_id}")
    String getMajorId();

    @Value("#{target.periodic_event_id}")
    String getEventId();
}

