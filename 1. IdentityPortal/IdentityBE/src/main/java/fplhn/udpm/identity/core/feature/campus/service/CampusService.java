package fplhn.udpm.identity.core.feature.campus.service;


import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.campus.model.request.CampusPaginationRequest;
import fplhn.udpm.identity.core.feature.campus.model.request.ModifyCampusRequest;

public interface CampusService {

    ResponseObject getAllCampus(CampusPaginationRequest request);

    ResponseObject createCampus(ModifyCampusRequest modifyCampusRequest);

    ResponseObject updateCampus(ModifyCampusRequest modifyCampusRequest, Long id);

    ResponseObject updateCampusStatus(Long id);

    ResponseObject getListCampus();

}
