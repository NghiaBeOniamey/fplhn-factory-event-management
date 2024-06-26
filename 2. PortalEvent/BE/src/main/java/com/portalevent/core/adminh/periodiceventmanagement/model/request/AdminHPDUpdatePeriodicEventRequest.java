package com.portalevent.core.adminh.periodiceventmanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdminHPDUpdatePeriodicEventRequest extends AdminHPDCreatePeriodicEventRequest {

    @NotBlank
    private String id;

}
