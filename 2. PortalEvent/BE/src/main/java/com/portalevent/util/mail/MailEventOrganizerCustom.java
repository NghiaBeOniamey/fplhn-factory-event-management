package com.portalevent.util.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MailEventOrganizerCustom {

    private String id;

    private String eventId;

    private String organizerId;

    private Integer eventRole;

    private String name;

    private String username;

    private String email;
}
