package com.portalevent.core.organizer.eventRegistered.model.response;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
public interface OerdEventResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.category_id}")
    String getCategoryId();

    @Value("#{target.status}")
    Short getStatus();

    @Value("#{target.background_path}")
    String getBackgroundPath();

    @Value("#{target.standee_path}")
    String getStandeePath();

}
