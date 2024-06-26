package com.portalevent.core.organizer.periodicevent.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface ORPeriodicEventObjectResponse extends IsIdentified {

    @Value("#{target.object_id}")
    String getObjectId();

    @Value("#{target.periodic_event_id}")
    String getEventId();
}

