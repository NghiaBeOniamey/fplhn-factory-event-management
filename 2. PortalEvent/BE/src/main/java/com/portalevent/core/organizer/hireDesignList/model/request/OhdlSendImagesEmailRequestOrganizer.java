package com.portalevent.core.organizer.hireDesignList.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OhdlSendImagesEmailRequestOrganizer {

    private String id;

    private String[] toMails;

    private String[] ccMails;

    private String subject;

    private String eventName;

    private String startDate;

    private String endDate;

    private String linkBackground;

    private String linkBanner;

    private String linkStandee;

}
