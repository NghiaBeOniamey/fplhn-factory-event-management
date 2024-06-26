package com.portalevent.core.adminho.synchronizedmanagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminHOMajorRequest {

    private Long majorId;

    private Long departmentId;

    private String majorCode;

    private String majorName;


}
