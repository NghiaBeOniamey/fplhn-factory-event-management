package com.portalevent.core.organizer.eventDetail.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author thangncph26123
 */
public interface OedEventOrganizerResponse extends IsIdentified {

    @Value("#{target.event_id}")
    String getEventId();

    @Value("#{target.organizer_id}")
    String getOrganizerId();

    @Value("#{target.event_role}")
    Integer getEventRole();

    @Value("#{target.organizer_code}")
    String getOrganizerCode();

    @Value("#{target.meeting_hour}")
    Double getMeetingHour();

}
