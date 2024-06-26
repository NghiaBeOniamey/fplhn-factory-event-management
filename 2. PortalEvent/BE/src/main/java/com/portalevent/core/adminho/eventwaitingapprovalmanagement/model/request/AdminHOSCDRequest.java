package com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminHOSCDRequest {

    @NotNull
    String idSemester;

    @NotNull
    String idCampus;

    @NotNull
    String idDepartment;

}
