package com.portalevent.util.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailAnnounceUpcomingEvent {
    private String[] mails;
    private String subject;
    private String date;
    private String eventCategory;
    private String eventName;
    private String locations;
    private String image;
    private String resources;
    private String lecturerList;
}
