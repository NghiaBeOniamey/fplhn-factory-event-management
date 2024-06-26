package com.portalevent.core.organizer.hireDesignList.model.response;

import com.portalevent.entity.EventLocation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Hứng data từ MySQL hiển thị location lên giao diện
 */
@Projection(types = EventLocation.class)
public interface OhdlEventLocationResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.event_id}")
    String getIdEvent();

    @Value("#{target.formality}")
    Integer getFormality();

    @Value("#{target.path}")
    String getPath();

    @Value("#{target.name}")
    String getName();

}
