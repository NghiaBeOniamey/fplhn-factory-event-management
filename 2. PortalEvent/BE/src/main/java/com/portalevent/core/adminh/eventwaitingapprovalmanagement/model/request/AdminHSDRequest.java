package com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminHSDRequest {

    @NotNull
    String idSemester;

    @NotNull
    String idDepartment;

}
