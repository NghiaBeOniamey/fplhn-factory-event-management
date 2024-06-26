package com.portalevent.core.organizer.eventDetail.model.response;

import com.portalevent.entity.Category;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {Category.class})
public interface OedCategoryResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();
}
