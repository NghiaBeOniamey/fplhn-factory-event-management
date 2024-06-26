package com.portalevent.util.mail;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailRequest {
    private String[] mails;
    private String subject;
    private String title;
    private String body;
}
