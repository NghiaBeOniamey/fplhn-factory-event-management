package fplhn.udpm.identity.core.feature.campus.model.request;

import fplhn.udpm.identity.core.common.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CampusPaginationRequest extends PageableRequest {

    private String xoaMemCoSo;

    private Long[] searchValues;

}
