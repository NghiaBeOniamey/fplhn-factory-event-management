package com.portalevent.core.organizer.eventRegistered.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OerdEventResponseDTO implements Serializable {

    private OerdEventResponse eventResponse;

    private List<OerdLocationResponse> listLocation;

    private String listUserNameOrganizer;
}
