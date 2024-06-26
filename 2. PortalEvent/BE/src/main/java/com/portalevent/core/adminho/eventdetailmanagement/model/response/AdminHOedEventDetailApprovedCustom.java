package com.portalevent.core.adminho.eventdetailmanagement.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHOedEventDetailApprovedCustom {

    private String id;

    private String name;

    private Long startTime;

    private Long endTime;

    private String categoryName;

    private String description;

    private String approverName;

    private String approverUserName;

    private String approverEmail;

    private String reason;

    private String expectedParticipant;

    private Integer status;

    private String background;

    private String banner;

    private String standee;

    private Integer eventType;

    private Integer isWaitApprovalPeriodically;

    private Boolean isHireDesignBanner;

    private Boolean isHireDesignBackground;

    private Boolean isHireDesignStandee;

    private Integer numberParticipants;

    private Integer participants;

    private Double AvgRate;

    private Boolean isNotEnoughTimeApproval;

    private String messageOverlapOrganizer;

    private String departmentName;

    private String majorName;

}
