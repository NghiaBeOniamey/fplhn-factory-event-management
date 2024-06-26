package com.portalevent.core.adminho.majormanagement.model.response;

import com.portalevent.entity.Department;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Department.class})
public interface AdminHODepartmentResponse {

    @Value("#{target.indexs}")
    Integer getIndex();

    String getCode();

    String getName();

    String getMailOfManager();

}
