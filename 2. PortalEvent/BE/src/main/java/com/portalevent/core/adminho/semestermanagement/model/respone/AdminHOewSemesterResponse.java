package com.portalevent.core.adminho.semestermanagement.model.respone;

import com.portalevent.entity.Semester;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Semester.class})
public interface AdminHOewSemesterResponse {
    @Value("#{target.stt}")
    Integer getStt();

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.startTime}")
    Long getStartTime();

    @Value("#{target.endTime}")
    Long getEndTime();

    @Value("#{target.startTimeFirstBlock}")
    Long getStartTimeFirstBlock();

    @Value("#{target.endTimeFirstBlock}")
    Long getEndTimeFirstBlock();

    @Value("#{target.startTimeSecondBlock}")
    Long getStartTimeSecondBlock();

    @Value("#{target.endTimeSecondBlock}")
    Long getEndTimeSecondBlock();
}
