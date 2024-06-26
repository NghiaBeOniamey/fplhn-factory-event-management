package fplhn.udpm.identity.core.feature.semester.model.response;

import fplhn.udpm.identity.core.common.IsIdentify;
import fplhn.udpm.identity.core.common.HasOrderNumber;

public interface SemesterResponse extends IsIdentify, HasOrderNumber {

    String getSemesterName();

    Long getStartTime();

    Long getEndTime();

    Long getStartTimeFirstBlock();

    Long getEndTimeFirstBlock();

    Long getStartTimeSecondBlock();

    Long getEndTimeSecondBlock();

}
