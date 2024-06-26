package com.portalevent.core.adminho.majormanagement.model.response;

import com.portalevent.entity.Campus;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Campus.class})
public interface AdminHOCampusResponse {

    String getCode();

    String getName();

}
