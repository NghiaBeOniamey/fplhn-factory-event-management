package com.portalevent.core.adminh.statisticseventmanagement.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdminHIdRequest {

    @NotNull
    String idSemester;

    @NotNull
    String idDepartment;

    @NotNull
    String idCategory;

}
