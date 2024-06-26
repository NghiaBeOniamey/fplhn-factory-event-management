package com.portalevent.util.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailOrganizerSendApproval {
    private String[] mails;
    private String subject;
    private String message;
    private String userNameAdd;
    private String lecturerName;
    private String eventName;
    private List<MailEventOrganizerCustom> lecturerList;
}
