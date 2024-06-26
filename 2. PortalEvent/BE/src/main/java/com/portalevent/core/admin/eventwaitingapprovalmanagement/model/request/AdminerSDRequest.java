package com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminerSDRequest {

    @NotNull
    String idSemester;

    @NotNull
    String idDepartment;

}
