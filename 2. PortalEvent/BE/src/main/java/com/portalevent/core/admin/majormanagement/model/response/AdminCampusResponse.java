package com.portalevent.core.admin.majormanagement.model.response;

import com.portalevent.entity.Campus;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Campus.class})
public interface AdminCampusResponse {

    String getCode();

    String getName();

}
