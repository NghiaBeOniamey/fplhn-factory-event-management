package com.portalevent.util.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailEvidenceResponce {
    private String eventId;

    private String name;

    private String path;

    private String description;

    private Integer evidenceType;
}
