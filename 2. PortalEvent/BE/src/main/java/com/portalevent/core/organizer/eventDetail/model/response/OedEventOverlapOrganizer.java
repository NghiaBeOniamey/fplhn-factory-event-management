package com.portalevent.core.organizer.eventDetail.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author SonPT
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OedEventOverlapOrganizer {

	private String id;

    private String name;

    private Long startTime;

    private Long endTime;

    private String organizerName;

    public OedEventOverlapOrganizer(String id, String name, Long startTime, Long endTime) {
        this.id = id;
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
