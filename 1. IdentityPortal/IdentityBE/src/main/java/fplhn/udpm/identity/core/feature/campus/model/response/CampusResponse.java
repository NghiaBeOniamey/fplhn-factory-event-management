package fplhn.udpm.identity.core.feature.campus.model.response;

import fplhn.udpm.identity.core.common.IsIdentify;
import fplhn.udpm.identity.core.common.HasOrderNumber;

public interface CampusResponse extends IsIdentify, HasOrderNumber {

    Long getCampusId();

    String getCampusCode();

    String getCampusName();

    String getCampusStatus();

}
