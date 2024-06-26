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
public class MailResourceRequest {
    private String[] mails;
    private String subject;
    private String eventName;
    private String category;
    private List<MailResourceResponse> resources;
}
