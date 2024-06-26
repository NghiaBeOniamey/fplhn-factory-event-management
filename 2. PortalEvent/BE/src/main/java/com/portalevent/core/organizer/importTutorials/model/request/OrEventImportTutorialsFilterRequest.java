package com.portalevent.core.organizer.importTutorials.model.request;

import com.portalevent.infrastructure.constant.EntityProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrEventImportTutorialsFilterRequest {

    private String idOrganizer;

    private String idSemester;

    private Short blockNumber;

}
