package com.portalevent.core.organizer.eventDetail.model.response;

import com.portalevent.entity.EventLocation;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author HoangDV
 */
@Projection(types = {EventLocation.class})
public interface OedEventLocationResponse extends IsIdentified {
    @Value("#{target.name}")
    String getName();

    @Value("#{target.formality}")
    int getFormality();

    @Value("#{target.path}")
    String getPath();
}
