package com.portalevent.core.organizer.eventRegistered.model.response;

import com.portalevent.entity.Category;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */
@Projection(types = {Category.class})
public interface OerdCategoryResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();
}
