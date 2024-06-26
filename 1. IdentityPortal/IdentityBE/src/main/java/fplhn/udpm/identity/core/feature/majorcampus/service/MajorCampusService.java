package fplhn.udpm.identity.core.feature.majorcampus.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.majorcampus.model.request.CreateMajorCampusRequest;
import fplhn.udpm.identity.core.feature.majorcampus.model.request.MajorCampusRequest;
import fplhn.udpm.identity.core.feature.majorcampus.model.request.UpdateMajorCampusRequest;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

@Validated
public interface MajorCampusService {

    ResponseObject getAllMajorCampus(@Valid MajorCampusRequest request);

    ResponseObject addMajorCampus(@Valid CreateMajorCampusRequest request);

    ResponseObject updateMajorCampus(Long majorCampusId, @Valid UpdateMajorCampusRequest request);

    ResponseObject changeStatusMajorCampus(Long majorCampusId);

    ResponseObject getDetailMajorCampus(Long majorCampusId);

    ResponseObject getAllStaff();

    ResponseObject getAllMajor();

}
