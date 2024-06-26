package com.portalevent.util.mail;

import com.portalevent.core.organizer.eventDetail.model.response.OedAgendaItemCustom;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventLocationResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventOrganizerCustom;
import com.portalevent.core.organizer.eventDetail.model.response.OedObjectResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedResourceResponse;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MailRequestApprovalEvent {
    private String[] mails;
    private String subject;
    private String eventName;
    private String nameHost;
    private List<OedEventOrganizerCustom> lecturers;
    private String[] content;
    private String date;
    private String category;
    private String typeEvent;
    private List<OedObjectResponse> objects;
    private List<OedEventLocationResponse> locations;
    private List<OedResourceResponse> resources;
    private List<OedAgendaItemCustom> agendas;
    private String expectedParticipants;
    private String linkBackground;
    private String linkBanner;
    private String emailHost;
}
