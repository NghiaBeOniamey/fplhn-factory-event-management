package fplhn.udpm.identity.core.feature.semester.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SemesterPaginationRequest extends PageableRequest {

    private String semesterName;

    private Long startTime;

    private Long endTime;

    private Long startTimeFirstBlock;

    private Long endTimeFirstBlock;

    private Long startTimeSecondBlock;

    private Long endTimeSecondBlock;

}
