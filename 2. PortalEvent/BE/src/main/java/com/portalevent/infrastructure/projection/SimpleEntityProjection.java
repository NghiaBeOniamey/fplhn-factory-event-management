package com.portalevent.infrastructure.projection;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author thangncph26123
 */
@Projection(types = {})
public interface SimpleEntityProjection extends IsIdentified {

    String getName();

}

