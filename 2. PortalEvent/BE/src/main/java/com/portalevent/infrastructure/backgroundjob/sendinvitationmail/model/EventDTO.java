package com.portalevent.infrastructure.backgroundjob.sendinvitationmail.model;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author HoangDV
 */
@Projection(types = {Event.class})
public interface EventDTO extends IsIdentified {
    @Value("#{target.category_name}")
    String getCategoryName();

    @Value("#{target.event_name}")
    String getEventName();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.background_path}")
    String getBackground();

    @Value("#{target.banner_path}")
    String getBanner();

    @Value("#{target.organizer_id}")
    String getOrganizerId();

    @Value("#{target.location_id}")
    String getLocationId();

    @Value("#{target.location_path}")
    String getLocationPath();

    @Value("#{target.location_name}")
    String getLocationName();

    @Value("#{target.formality}")
    Integer getFormality();

    @Value("#{target.resource_id}")
    String getResourceId();

    @Value("#{target.resource_name}")
    String getResourceName();

    @Value("#{target.resource_path}")
    String getResourcePath();

}
