package com.portalevent.core.organizer.eventDetail.model.response;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author HoangDV
 */
public interface OedMajorResponse extends IsIdentified {
    @Value("#{target.code}")
    String getCode();
    @Value("#{target.name}")
    String getName();
}
