package com.portalevent.core.approver.subjectevent.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface AseSubjectListSubjectResponse {

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

}
