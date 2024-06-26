package com.portalevent.core.approver.eventdetail.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

public interface AedMajorResponse extends IsIdentified {
    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();
}
