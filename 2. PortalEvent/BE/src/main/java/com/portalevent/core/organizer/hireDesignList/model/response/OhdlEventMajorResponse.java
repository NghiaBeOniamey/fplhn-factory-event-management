package com.portalevent.core.organizer.hireDesignList.model.response;

import com.portalevent.entity.Major;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Hứng data MySQL hiển thị major lên giao diện
 */
@Projection(types = Major.class)
public interface OhdlEventMajorResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();
}
