package com.portalevent.util.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailEventUpdateWhenApproved {
    private String[] mails;
    private String subject;
    private String eventName;
    private String userNameChange;
    private String[] content;
    private String date;
    private String oldDate;
    private String category;
    private String typeEvent;
    private String[] department;
    private String[] major;
    private String expectedParticipants;
}
