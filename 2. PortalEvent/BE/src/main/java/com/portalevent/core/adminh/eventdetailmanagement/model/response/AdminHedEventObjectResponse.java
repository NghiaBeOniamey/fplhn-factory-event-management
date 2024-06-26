package com.portalevent.core.adminh.eventdetailmanagement.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface AdminHedEventObjectResponse extends IsIdentified {

    @Value("#{target.object_id}")
    String getObjectId();

    @Value("#{target.event_id}")
    String getEventId();
}