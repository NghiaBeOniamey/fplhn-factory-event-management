package com.portalevent.core.adminho.semestermanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHOSemesterRequest {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private Long startTime;

    @NotNull
    private Long endTime;

    @NotNull
    private Long startTimeFirstBlock;

    @NotNull
    private Long endTimeFirstBlock;

    @NotNull
    private Long startTimeSecondBlock;

    @NotNull
    private Long endTimeSecondBlock;

}
