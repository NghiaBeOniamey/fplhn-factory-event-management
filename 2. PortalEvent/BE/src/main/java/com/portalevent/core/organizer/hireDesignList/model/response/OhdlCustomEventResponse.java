package com.portalevent.core.organizer.hireDesignList.model.response;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * custom data từ repository tích hợp với lấy data từ API
 */

@Getter
@Setter
public class OhdlCustomEventResponse {

    private String id;

    private String name;

    private Long startTime;

    private Long endTime;

    private Integer eventType;

    private String semester;

    private String formality;

    private String userNameOrganizer;

    private Boolean isHireDesignStandee;

    private Boolean isHireDesignBanner;

    private Boolean isHireDesignBackground;

    private Boolean isHireLocation;

    private String nameLocation;

    private String nameMajor;

    private String nameObject;

    private String nameCategory;

    List<String> typeBookings = new ArrayList<>();

}
