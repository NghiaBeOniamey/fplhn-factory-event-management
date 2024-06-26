package com.portalevent.core.approver.eventwaitingapproval.model.respone;

import com.portalevent.entity.Category;
import com.portalevent.entity.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Event.class, Category.class})
public interface AewaEventDetailApprovedResponse {
    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.startTime}")
    Long getStartTime();

    @Value("#{target.endTime}")
    Long getEndTime();

    @Value("#{target.formality}")
    Long getFormality();

    @Value("#{target.categoryName}")
    String getCategoryName();

    @Value("#{target.majorName}")
    String getMajorName();

    @Value("#{target.blockName}")
    String getBlockName();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.location}")
    String getLocation();

    @Value("#{target.approverName}")
    String getApproverName();

    @Value("#{target.reason}")
    String getReason();

    @Value("#{target.expectedParticipant}")
    String getExpectedParticipant();

    @Value("#{target.status}")
    int getStatus();
}
