package com.portalevent.core.adminho.synchronizedmanagement.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AdminHOSemesterRequest {

    private Long id;

    private Long startTime;

    private Long endTime;

    private Long startTimeFirstBlock;

    private Long endTimeFirstBlock;

    private Long startTimeSecondBlock;

    private Long endTimeSecondBlock;

    private String semesterName;

}
