package com.portalevent.core.approver.periodicevent.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class APDUpdatePeriodicEventRequest extends APDCreatePeriodicEventRequest{

    @NotBlank
    private String id;

}
