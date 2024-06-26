package com.portalevent.core.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentResponse {

    private Long departmentId;

    private String departmentCode;

    private String departmentName;

    private String emailHeadDepartmentFe;

    private Long campusId;

    private String campusCode;

}