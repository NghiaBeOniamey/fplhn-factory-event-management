package fplhn.udpm.identity.core.feature.semester.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.semester.model.request.ModifySemesterRequest;
import fplhn.udpm.identity.core.feature.semester.model.request.SemesterPaginationRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SemesterService {

    ResponseObject getAllSemester(@Valid SemesterPaginationRequest request);

    ResponseObject createSemester(@Valid ModifySemesterRequest request);

    ResponseObject updateSemester(Long semesterId, @Valid ModifySemesterRequest request);

    ResponseObject getDetailSemester(Long semesterId);

}
