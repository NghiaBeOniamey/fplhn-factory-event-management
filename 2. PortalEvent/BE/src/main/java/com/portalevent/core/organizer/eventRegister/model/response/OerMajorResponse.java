package com.portalevent.core.organizer.eventRegister.model.response;

import com.portalevent.entity.Category;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */
@Projection(types = {Category.class})
public interface OerMajorResponse extends IsIdentified {
    @Value("#{target.code}")
    String getCode();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.mail_of_manager}")
    String getMailOfManager();

    @Value("#{target.department_id}")
    Long getDepartmentId();
}
