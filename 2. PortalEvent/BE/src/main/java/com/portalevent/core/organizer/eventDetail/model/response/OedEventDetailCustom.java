package com.portalevent.core.organizer.eventDetail.model.response;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Getter
@Setter
public class OedEventDetailCustom {
    private String id;
    private String semester;
    private String semesterId;
    private Long semesterStart;
    private Long semesterEnd;
    private Long startTimeFirstBlock;
    private Long endTimeFirstBlock;
    private Long startTimeSecondBlock;
    private Long endTimeSecondBlock;
    private String name;
    private String category;
    private Integer expectedParticipants;
    private Boolean block;
    private Long startTime;
    private Long endTime;
    private String description;
    private Integer status;
    private String reason;
    private String background;
    private String banner;
    private String standee;
    private Integer eventType;
    private String approveName;
    private Long createdDate;
    private Integer countParticipant; // Số người đăng ký
    private Integer countNumberParticipant; // Số người điểm danh hê thống
    private Integer numberParticipant; // Số người điểm danh báo cáo
    private String percentage; // Tỉ lệ đánh giá
    private Boolean isHireLocation;
    private Boolean isHireDesignBanner;
    private Boolean isHireDesignBg;
    private Boolean isHireDesignStandee;
    private Boolean isOpenRegistration;
    private Boolean isAttendance;
    private Integer isWaitApprovalPeriodically;
    private String approvalPeriodicallyReason;
    private Boolean isConversionHoneyRequest;
}
