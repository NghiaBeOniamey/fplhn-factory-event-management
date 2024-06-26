package com.portalevent.core.participant.home.model.response;

import com.portalevent.infrastructure.constant.EventStatus;
import com.portalevent.infrastructure.constant.EventType;
import com.portalevent.infrastructure.constant.Formality;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author SonPT
 */

@NoArgsConstructor
@Getter
@Setter
public class PhDetailEventResponse {

    private String id;

    private String name;

    private Long startTime;

    private Long endTime;

    private Boolean isOpenAttendance;

    private Boolean isOpenRegistration;

    private Boolean isRegisted;

    private Boolean isAttended;

    private String category;

    private EventType eventType;

    private String backgroundPath;

    private String bannerPath;

    private String description;

    private String locationId;

    private String locationName;

    private Formality formality;

    private String locationPath;

    private String majorId;

    private String majorName;

    private String objectId;

    private String objectName;

    private String resourceId;

    private String resourceName;

    private String resourcePath;

    private String organizerId;

    private EventStatus status;

    private List<String> listOrganizer;

    public PhDetailEventResponse(String id, String name, Long startTime, Long endTime, Boolean isOpenAttendance, Boolean isOpenRegistration, String category,
                                 EventType eventType, String backgroundPath, String bannerPath, String description,
                                 String locationId, String locationName, Formality formality, String locationPath,
                                 String majorId, String majorName, String objectId, String objectName,
                                 String resourceId, String resourceName, String resourcePath, EventStatus status, String organizerId) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isOpenAttendance = isOpenAttendance;
        this.isOpenRegistration = isOpenRegistration;
        this.category = category;
        this.eventType = eventType;
        this.backgroundPath = backgroundPath;
        this.bannerPath = bannerPath;
        this.description = description;
        this.locationId = locationId;
        this.locationName = locationName;
        this.formality = formality;
        this.locationPath = locationPath;
        this.majorId = majorId;
        this.majorName = majorName;
        this.objectId = objectId;
        this.objectName = objectName;
        this.resourceId = resourceId;
        this.resourceName = resourceName;
        this.resourcePath = resourcePath;
        this.status = status;
        this.organizerId = organizerId;
    }

}
