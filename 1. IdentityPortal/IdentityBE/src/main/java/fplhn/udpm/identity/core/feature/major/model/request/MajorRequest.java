package fplhn.udpm.identity.core.feature.major.model.request;

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
public class MajorRequest extends PageableRequest {

    @NotNull
    private Long departmentId;

    private String majorCode;

    private String majorName;

}
