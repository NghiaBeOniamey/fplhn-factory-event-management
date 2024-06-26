package com.portalevent.core.organizer.eventDetail.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OedEvidenceRequest {

    @NotBlank
    private String idEvent;

    @NotBlank
    @NotNull
    private String name;

    @NotBlank
    @NotNull
    private String path;

    private String description;

    @NotNull
    private Integer evidenceType;

}
