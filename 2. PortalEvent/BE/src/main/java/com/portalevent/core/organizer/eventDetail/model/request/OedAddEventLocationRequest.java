package com.portalevent.core.organizer.eventDetail.model.request;

import com.portalevent.infrastructure.constant.Formality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * @author HoangDV
 */
@Getter
@Setter
public class OedAddEventLocationRequest {

    @NotBlank
    private String idEvent;

    @NotNull
    private Formality formality;

    @NotBlank
    private String name;

    @NotBlank
    private String path;

}
