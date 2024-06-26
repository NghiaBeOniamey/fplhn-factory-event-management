package com.portalevent.core.system.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author SonPT
 */

@Getter
@Setter
public class ShowHistoryRequest {

    private Integer page;

    private Integer size;

    private String displayName;

    private String eventId;

}
