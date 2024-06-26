package com.portalevent.core.organizer.eventRegistered.model.response;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
public interface OerdLocationResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.formality}")
    Short getFormality();

    @Value("#{target.path}")
    String getPath();

}
