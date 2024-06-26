package com.portalevent.core.approver.eventdetail.model.response;

import com.portalevent.entity.AgendaItem;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {AgendaItem.class})
public interface AedAgendaItemsDetailResponse extends IsIdentified {
    @Value("#{target.indexs}")
    Integer getIndex();

    @Value("#{target.startTime}")
    String getStartTime();

    @Value("#{target.endTime}")
    String getEndTime();

    @Value("#{target.organizerId}")
    String getOrganizerId();

    @Value("#{target.description}")
    String getDescription();
}
