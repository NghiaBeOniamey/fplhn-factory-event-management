package com.portalevent.core.adminho.periodiceventmanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdminHOPDUpdatePeriodicEventRequest extends AdminHOPDCreatePeriodicEventRequest {

    @NotBlank
    private String id;

}
