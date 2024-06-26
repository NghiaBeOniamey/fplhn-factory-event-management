package com.portalevent.core.organizer.eventRegister.model.response;

import com.portalevent.entity.Department;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */
@Projection(types = {Department.class})
public interface OerDepartmentResponse extends IsIdentified {
    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.mail_of_manager}")
    String getMailOfManager();

    @Value("#{target.department_id}")
    Long getDepartmentId();
}
