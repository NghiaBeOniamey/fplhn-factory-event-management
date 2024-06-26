package com.portalevent.core.adminho.eventdetailmanagement.model.response;

import com.portalevent.entity.Category;
import com.portalevent.entity.Event;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Event.class, Category.class})
public interface AdminHOedEventDetailApprovedResponse {
    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.startTime}")
    Long getStartTime();

    @Value("#{target.endTime}")
    Long getEndTime();

    @Value("#{target.categoryName}")
    String getCategoryName();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.approverId}")
    String getApproverId();

    @Value("#{target.reason}")
    String getReason();

    @Value("#{target.expectedParticipant}")
    String getExpectedParticipant();

    @Value("#{target.status}")
    Integer getStatus();

    @Value("#{target.background}")
    String getBackground();

    @Value("#{target.banner}")
    String getBanner();

    @Value("#{target.standee}")
    String getStandee();

    @Value("#{target.event_type}")
    Integer getEventType();

    @Value("#{target.isHireDesignBanner}")
    Boolean getIsHireDesignBanner();

    @Value("#{target.isHireDesignBackground}")
    Boolean getIsHireDesignBackground();

    @Value("#{target.isHireDesignStandee}")
    Boolean getIsHireDesignStandee();

    @Value("#{target.numberParticipants}")
    Integer getNumberParticipants();

    @Value("#{target.participants}")
    Integer getParticipants();

    @Value("#{target.isWaitApprovalPeriodically}")
    Integer getIsWaitApprovalPeriodically();

    @Value("#{target.avgRate}")
    Double getAvgRate();
    @Value("#{target.is_not_enough_time_approval}")
    Boolean getIsNotEnoughTimeApproval();

    @Value("#{target.departmentName}")
    String getDepartmentName();

    @Value("#{target.majorName}")
    String getMajorName();

}
