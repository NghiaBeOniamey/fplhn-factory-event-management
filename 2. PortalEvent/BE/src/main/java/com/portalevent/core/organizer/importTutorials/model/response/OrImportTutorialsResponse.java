package com.portalevent.core.organizer.importTutorials.model.response;

import com.portalevent.entity.Event;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Event.class})
public interface OrImportTutorialsResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.start_time}")
    String getStartTime();

    @Value("#{target.end_time}")
    String getEndTime();

    @Value("#{target.event_major_id}")
    String getEventMajorId();

    @Value("#{target.event_object_id}")
    String getEventObjectId();
}
