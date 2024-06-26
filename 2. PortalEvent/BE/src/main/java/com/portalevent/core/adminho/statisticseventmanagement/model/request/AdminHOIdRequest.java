package com.portalevent.core.adminho.statisticseventmanagement.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminHOIdRequest {

    @NotNull
    String idSemester;

    @NotNull
    Long idCampus;

    @NotNull
    Long idDepartment;

    @NotNull
    String idCategory;

}
