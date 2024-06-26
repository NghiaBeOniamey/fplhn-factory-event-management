package com.portalevent.core.organizer.eventRegister.model.response;

import com.portalevent.entity.Object;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author SonPT
 */
@Projection(types = {Object.class})
public interface OerObjectResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();
}
