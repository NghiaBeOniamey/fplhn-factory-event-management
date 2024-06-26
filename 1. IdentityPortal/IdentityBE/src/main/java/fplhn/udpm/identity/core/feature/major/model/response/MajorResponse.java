package fplhn.udpm.identity.core.feature.major.model.response;


import fplhn.udpm.identity.core.common.IsIdentify;
import fplhn.udpm.identity.core.common.HasOrderNumber;

public interface MajorResponse extends IsIdentify, HasOrderNumber {

    Long getMajorId();

    String getMajorCode();

    String getMajorName();

    String getMajorStatus();

}
