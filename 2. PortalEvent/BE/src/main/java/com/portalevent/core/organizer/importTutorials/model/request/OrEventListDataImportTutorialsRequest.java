package com.portalevent.core.organizer.importTutorials.model.request;

import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.constant.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class OrEventListDataImportTutorialsRequest {

    @NotNull
    @NotBlank
    @NotEmpty
    @Length(max = EntityProperties.LENGTH_NAME)
    private String name;

    private String startTime;

    private String endTime;

    private String majorStr;

    private String objectStr;

    private String locationStr;

}
