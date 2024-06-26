package com.portalevent.core.organizer.eventDetail.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface OedInvitationTimeResponse{
    @Value("#{target.id}")
    String getId();

    @Value("#{target.indexs}")
    String getIndex();

    @Value("#{target.eventId}")
    String getEventId();

    @Value("#{target.time}")
    Long getTime();

    @Value("#{target.status}")
    Boolean getStatus();
}
