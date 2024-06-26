package com.portalevent.core.admin.periodiceventmanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdminerDPDUpdatePeriodicEventRequest extends AdminerDPDCreatePeriodicEventRequest {

    @NotBlank
    private String id;

}
