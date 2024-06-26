package fplhn.udpm.identity.core.feature.majorcampus.model.response;

import fplhn.udpm.identity.core.common.HasOrderNumber;
import fplhn.udpm.identity.core.common.IsIdentify;

public interface MajorCampusResponse extends IsIdentify, HasOrderNumber {

    Long getMajorCampusId();

    String getMajorCodeName();

    String getHeadMajorCodeName();

    String getMajorCampusStatus();

    String getCampusCode();

}
