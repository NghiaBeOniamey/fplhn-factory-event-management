package fplhn.udpm.identity.core.feature.major.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.major.model.request.CreateMajorRequest;
import fplhn.udpm.identity.core.feature.major.model.request.MajorRequest;
import fplhn.udpm.identity.core.feature.major.model.request.UpdateMajorRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface MajorService {

    ResponseObject findAllMajor(@Valid MajorRequest request);

    ResponseObject addMajor(@Valid CreateMajorRequest request);

    ResponseObject updateMajor(@Valid UpdateMajorRequest request, Long id);

    ResponseObject changeStatus(Long majorId);

    ResponseObject findDetailMajor(Long majorId);

}
