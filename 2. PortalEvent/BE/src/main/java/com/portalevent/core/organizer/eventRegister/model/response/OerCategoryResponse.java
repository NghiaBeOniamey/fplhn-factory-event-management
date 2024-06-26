package com.portalevent.core.organizer.eventRegister.model.response;

import com.portalevent.entity.Category;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */
@Projection(types = {Category.class})
public interface OerCategoryResponse extends IsIdentified {
    @Value("#{target.name}")
    String getName();
}
