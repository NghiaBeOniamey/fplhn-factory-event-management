package fplhn.udpm.identity.core.feature.module.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModulePaginationRequest extends PageableRequest {

    private String deleteStatus;

    private Long[] listId;

}
