package com.portalevent.core.organizer.eventDetail.model.response;

import com.portalevent.entity.Evidence;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Evidence.class})
public interface OedEventEvidenceResponse extends IsIdentified {
    @Value("#{target.eventId}")
    String getEventId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.path}")
    String getPath();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.evidenceType}")
    Integer getEvidenceType();
}
