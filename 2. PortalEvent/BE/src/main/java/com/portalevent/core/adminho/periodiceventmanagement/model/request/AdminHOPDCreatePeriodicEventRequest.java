package com.portalevent.core.adminho.periodiceventmanagement.model.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author thangncph26123
 */
@Getter
@Setter
public class AdminHOPDCreatePeriodicEventRequest {

    @NotBlank
    private String name;

    @NotNull
    private Integer eventType;

    @NotBlank
    private String categoryId;

    @NotNull
    @Max(value = 25000)
    @Min(value = 1)
    private Short expectedParticipants;

    private String description;

    private List<String> listObject;

    private List<String> listMajor;

}
