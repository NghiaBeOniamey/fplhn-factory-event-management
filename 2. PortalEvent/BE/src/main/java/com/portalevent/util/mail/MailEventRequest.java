package com.portalevent.util.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailEventRequest {
    private String[] mails;
    private String subject;
    private String eventName;
    private String[] lecturer;
    private String[] content;
    private String time;
    private String date;
    private String typeEvent;
    private String linkZoom;
    private String linkBackground;
}
