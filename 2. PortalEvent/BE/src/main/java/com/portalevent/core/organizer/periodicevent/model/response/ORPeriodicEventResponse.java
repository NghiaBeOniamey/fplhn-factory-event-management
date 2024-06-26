package com.portalevent.core.organizer.periodicevent.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface ORPeriodicEventResponse extends IsIdentified {

    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.category_name}")
    String getCategoryName();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.event_type}")
    Integer getEventType();

    @Value("#{target.expected_participants}")
    Short getExpectedParticipants();

    @Value("#{target.major}")
    String getMajor();

    @Value("#{target.object}")
    String getObject();
}
