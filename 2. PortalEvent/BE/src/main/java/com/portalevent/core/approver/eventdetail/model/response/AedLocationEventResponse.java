package com.portalevent.core.approver.eventdetail.model.response;

import com.portalevent.entity.EventLocation;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = EventLocation.class)
public interface AedLocationEventResponse extends IsIdentified {
    @Value("#{target.formality}")
    String getFormality();

    @Value("#{target.path}")
    String getPath();

    @Value("#{target.name}")
    String getName();
}
