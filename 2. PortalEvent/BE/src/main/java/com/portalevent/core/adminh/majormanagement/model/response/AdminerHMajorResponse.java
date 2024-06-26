package com.portalevent.core.adminh.majormanagement.model.response;

import com.portalevent.entity.Major;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Major.class})
public interface AdminerHMajorResponse {

    @Value("#{target.indexs}")
    Integer getIndex();

    String getId();

    String getCode();

    String getName();

    String getMailOfManager();

}
