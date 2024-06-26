package com.portalevent.core.organizer.eventInSemester.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface OeisEventInSemesterResponse {
    @Value("#{target.indexs}")
    Integer getIndex();

    String getId();

    String getName();

    Integer getStatus();

    String getCategory();

    String getObject();

    String getFormality();

    Integer getExpectedParticipant();

    Integer getNumberParticipant();

    String getOrganizer();
}
