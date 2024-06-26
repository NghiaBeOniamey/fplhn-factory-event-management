package com.portalevent.core.organizer.eventDetail.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface OedAgendaItemResponse extends IsIdentified {

    @Value("#{target.event_id}")
    String getEventId();

    @Value("#{target.organizer_id}")
    String getOrganizerId();

    @Value("#{target.organizer_code}")
    String getOrganizerCode();

    @Value("#{target.start_time}")
    String getStartTime();

    @Value("#{target.end_time}")
    String getEndTime();

    @Value("#{target.description}")
    String getDescription();
}
