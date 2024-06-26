package fplhn.udpm.identity.core.connection.model.response;

import fplhn.udpm.identity.core.common.IsIdentify;

public interface SemesterConnectionResponse extends IsIdentify {

    String getSemesterName();

    Long getStartTime();

    Long getEndTime();

    Long getStartTimeFirstBlock();

    Long getEndTimeFirstBlock();

    Long getStartTimeSecondBlock();

    Long getEndTimeSecondBlock();

}
