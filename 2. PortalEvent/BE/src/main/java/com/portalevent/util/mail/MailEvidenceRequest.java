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
public class MailEvidenceRequest {
    private String[] mails;
    private String subject;
    private String eventName;
    private String time;
    private String date;
    private String countParticipant;
    private String numberParticipants;
    private String eventType;
    private String percentage;
    private List<MailEvidenceResponce> evidences;
}
