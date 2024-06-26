package com.portalevent.core.organizer.importTutorials.model.request;

import com.portalevent.infrastructure.constant.EntityProperties;
import com.portalevent.infrastructure.constant.EventType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@Setter
public class OrEventImportTutorialsRequest {

    @Length(max = EntityProperties.LENGTH_ID)
    private String idSemester;

    private Boolean blockNumber;

    List<OrEventListDataImportTutorialsRequest> listDataImport;

}
