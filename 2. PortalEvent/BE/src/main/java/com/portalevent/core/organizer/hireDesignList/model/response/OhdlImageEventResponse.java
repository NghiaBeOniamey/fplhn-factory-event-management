package com.portalevent.core.organizer.hireDesignList.model.response;

import com.portalevent.entity.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Hiển thị images event hire design lên giao diện
 */
@Projection(types = Event.class)
public interface OhdlImageEventResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    Long getStartDate();

    @Value("#{target.end_time}")
    Long getEndDate();

    @Value("#{target.background_path}")
    String getBackgroundPath();

    @Value("#{target.banner_path}")
    String getBannerPath();

    @Value("#{target.standee_path}")
    String getStandeePath();

}
