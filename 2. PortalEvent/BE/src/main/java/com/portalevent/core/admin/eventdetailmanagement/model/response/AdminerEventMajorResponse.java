package com.portalevent.core.admin.eventdetailmanagement.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface AdminerEventMajorResponse extends IsIdentified {

    @Value("#{target.major_id}")
    String getMajorId();

    @Value("#{target.event_id}")
    String getEventId();
}
