package com.portalevent.core.admin.semestermanagement.model.respone;

import com.portalevent.entity.Semester;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Semester.class})
public interface AdminerNameSemesterResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

}
