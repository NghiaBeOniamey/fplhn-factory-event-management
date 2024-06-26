package com.portalevent.core.admin.statisticseventmanagement.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminerSemesterMajorRequest {

    @NotNull
    String idSemester;

    @NotNull
    String idDepartment;

}
