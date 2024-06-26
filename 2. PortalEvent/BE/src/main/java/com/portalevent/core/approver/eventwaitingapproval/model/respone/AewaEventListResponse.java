package com.portalevent.core.approver.eventwaitingapproval.model.respone;

import com.portalevent.entity.Category;
import com.portalevent.entity.Event;
import com.portalevent.entity.Major;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Event.class, Category.class, Major.class})
public interface AewaEventListResponse {
    @Value("#{target.indexs}")
    Integer getIndex();

    @Value("#{target.eventId}")
    String getId();

    @Value("#{target.eventName}")
    String getName();

    @Value("#{target.eventStartTime}")
    Long getStartTime();

    @Value("#{target.eventEndTime}")
    Long getEndTime();

    @Value("#{target.categoryName}")
    String getNameCategory();

    @Value("#{target.status}")
    String getStatus();
}
