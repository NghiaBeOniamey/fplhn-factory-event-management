package com.portalevent.core.adminho.synchronizedmanagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminHODepartmentCampusRequest {

    private Long departmentCampusId;

    private Long departmentId;

    private Long campusId;

    private String emailHeadDepartmentFpt;

    private String emailHeadDepartmentFe;

}
