package com.portalevent.core.adminh.statisticseventmanagement.model.response;

import com.portalevent.entity.Department;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author HoangDV
 */
@Projection(types = {Department.class})
public interface AdminHseDepartmentResponse extends IsIdentified {

    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

}
