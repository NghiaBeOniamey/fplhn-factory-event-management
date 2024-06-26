package com.portalevent.core.organizer.importTutorials.model.response;

import com.portalevent.entity.Major;
import com.portalevent.entity.base.IsIdentified;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Object.class})
public interface OrImportTutorialsObjectResponse extends IsIdentified {

    @Value("#{target.name}")
    String getName();
}
