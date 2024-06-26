package com.portalevent.core.adminho.synchronizedmanagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminHOCampusRequest {

    private Long campusId;

    private String campusCode;

    private String campusName;

}
