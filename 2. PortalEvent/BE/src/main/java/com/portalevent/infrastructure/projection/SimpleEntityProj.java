package com.portalevent.infrastructure.projection;

import com.portalevent.entity.base.IsIdentified;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */
@Projection(types = {})
public interface SimpleEntityProj extends IsIdentified {

    String getEmail();

}
