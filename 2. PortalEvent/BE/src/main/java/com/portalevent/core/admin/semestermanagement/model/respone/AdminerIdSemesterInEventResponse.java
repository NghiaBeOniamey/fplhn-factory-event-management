package com.portalevent.core.admin.semestermanagement.model.respone;

import com.portalevent.entity.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Event.class})
public interface AdminerIdSemesterInEventResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.semester_id}")
    String getIdSemester();

    @Value("#{target.name}")
    String getName();

}
