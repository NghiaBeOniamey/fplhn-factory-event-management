package fplhn.udpm.identity.core.feature.majorcampus.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MajorCampusRequest extends PageableRequest {

    private String headMajorName;

    private String majorCode;

    private String majorName;

    private String headMajorCode;

    @NotNull
    private Long departmentCampusId;

}
