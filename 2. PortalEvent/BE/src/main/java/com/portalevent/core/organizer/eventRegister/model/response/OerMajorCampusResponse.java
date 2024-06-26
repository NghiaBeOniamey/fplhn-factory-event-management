package com.portalevent.core.organizer.eventRegister.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface OerMajorCampusResponse {

    @Value("#{target.majorCampusId}")
    String getId();

    @Value("#{target.departmentCampusId}")
    Long getDepartmentCampusId();

    @Value("#{target.departmentId}")
    String getDepartmentId();

    @Value("#{target.majorName}")
    String getName();

}
