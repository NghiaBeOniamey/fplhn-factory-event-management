package com.portalevent.core.admin.majormanagement.model.response;

import com.portalevent.entity.Department;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Department.class})
public interface AdminDepartmentResponse {

    @Value("#{target.indexs}")
    Integer getIndex();

    String getCode();

    String getName();

    String getMailOfManager();

}
