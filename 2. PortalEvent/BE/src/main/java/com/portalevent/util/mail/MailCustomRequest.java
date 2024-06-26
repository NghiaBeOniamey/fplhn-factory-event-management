package com.portalevent.util.mail;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MailCustomRequest {
    private String[] mails;
    private String subject;
    private String eventName;
    private String oganizerName;
    private String date;
    private String category;
    private String typeEvent;
    private List<MailEventOrganizerCustom> lecturerList;
    private List<MailObjectResponse> objects;
    private String reason;
}
