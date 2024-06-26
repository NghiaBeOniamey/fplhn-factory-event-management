package com.portalevent.core.organizer.importTutorials.model.response;

import com.portalevent.entity.base.IsIdentified;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

@Setter
@Getter
public class OrImportTutorialsResponseDTO{
    private OrImportTutorialsResponse tutorial;

    private String majorName;

    private String objectName;

    private String locationPath;
}
