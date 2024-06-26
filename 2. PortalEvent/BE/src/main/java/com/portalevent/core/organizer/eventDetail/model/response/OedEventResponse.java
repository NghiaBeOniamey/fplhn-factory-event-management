package com.portalevent.core.organizer.eventDetail.model.response;

import com.portalevent.entity.Event;
import com.portalevent.entity.Major;
import com.portalevent.entity.base.IsIdentified;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author HoangDV
 * @description
 * @update
 * @since 06/07/2023 10:23
 */
@Projection(types = {Event.class})
public interface OedEventResponse extends IsIdentified {

    @Value("#{target.semester}")
    String getSemester();

    @Value("#{target.semesterId}")
    String getSemesterId();

    @Value("#{target.semesterStart}")
    Long getSemesterStart();

    @Value("#{target.semesterEnd}")
    Long getSemesterEnd();

    @Value("#{target.startTimeFirstBlock}")
    Long getStartTimeFirstBlock();

    @Value("#{target.endTimeFirstBlock}")
    Long getEndTimeFirstBlock();

    @Value("#{target.startTimeSecondBlock}")
    Long getStartTimeSecondBlock();

    @Value("#{target.endTimeSecondBlock}")
    Long getEndTimeSecondBlock();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.category}")
    String getCategory();

    @Value("#{target.expected_participants}")
    Integer getExpectedParticipants();

    @Value("#{target.block_number}")
    Boolean getBlock();

    @Value("#{target.start_time}")
    Long getStartTime();

    @Value("#{target.end_time}")
    Long getEndTime();

    @Value("#{target.description}")
    String getDescription();

    @Value("#{target.status}")
    Integer getStatus();

    @Value("#{target.reason}")
    String getReason();

    @Value("#{target.background}")
    String getBackground();

    @Value("#{target.banner}")
    String getBanner();

    @Value("#{target.standee}")
    String getStandee();

    @Value("#{target.event_type}")
    Integer getEventType();

    @Value("#{target.approver_code}")
    String getApprover();

    @Value("#{target.created_date}")
    Long getCreatedDate();

    @Value("#{target.number_participants}")
    Integer getNumberparticipant();

    @Value("#{target.is_hire_location}")
    Boolean getIsHireLocation();

    @Value("#{target.is_hire_design_bg}")
    Boolean getIsHireDesignBG();

    @Value("#{target.is_hire_design_banner}")
    Boolean getIsHireDesignBanner();

    @Value("#{target.is_hire_design_standee}")
    Boolean getIsHireDesignStandee();

    @Value("#{target.is_attendance}")
    Boolean getIsAttendance();

    @Value("#{target.is_open_registration}")
    Boolean getIsOpenRegistration();

    @Value("#{target.is_wait_approval_periodically}")
    Integer getIsWaitApprovalPeriodically();

    @Value("#{target.approval_periocially_reason}")
    String getApprovalPeriodicallyReason();

    @Value("#{target.is_conversion_honey_request}")
    Boolean getIsConversionHoneyRequest();
}
