package com.portalevent.core.organizer.eventRegister.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author SonPT
 */
@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class OerCreateResourceResponse {

    private String name;

    private String path;

    private String description;
}
