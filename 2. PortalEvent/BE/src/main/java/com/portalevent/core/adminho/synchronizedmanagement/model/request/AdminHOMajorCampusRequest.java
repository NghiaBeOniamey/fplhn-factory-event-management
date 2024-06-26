package com.portalevent.core.adminho.synchronizedmanagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminHOMajorCampusRequest {

    private Long majorCampusId;

    private Long departmentCampusId;

    private Long majorId;

    private String emailHeadMajorFpt;

    private String emailHeadMajorFE;

}
