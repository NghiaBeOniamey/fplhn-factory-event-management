package fplhn.udpm.identity.core.feature.semester.model.response;

import fplhn.udpm.identity.core.common.IsIdentify;

public interface DetailSemesterResponse extends IsIdentify {

    String getSemesterName();

    Long getStartTime();

    Long getEndTime();

    Long getStartTimeFirstBlock();

    Long getEndTimeFirstBlock();

    Long getStartTimeSecondBlock();

    Long getEndTimeSecondBlock();

}
