package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HoangDV
 */
@Getter
@Setter
public class OedCreateResourceRequest extends OedBaseResourceRequest{
    @NotBlank
    private String idEvent;
}
