package com.portalevent.core.organizer.eventRegister.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface OerDepartmentCampusResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getDepartmentCode();

    @Value("#{target.department_name}")
    String getDepartmentName();

    @Value("#{target.department_campus_id}")
    Long getDepartmentCampusId();

}
