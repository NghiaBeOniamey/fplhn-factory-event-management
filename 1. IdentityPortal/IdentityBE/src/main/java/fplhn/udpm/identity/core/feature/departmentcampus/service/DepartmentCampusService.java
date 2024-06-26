package fplhn.udpm.identity.core.feature.departmentcampus.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.departmentcampus.model.request.DepartmentCampusDetailRequest;
import fplhn.udpm.identity.core.feature.departmentcampus.model.request.ModifyDepartmentCampusRequest;

public interface DepartmentCampusService {

    ResponseObject<?> getAllDepartmentCampus(Long id, DepartmentCampusDetailRequest request);

    ResponseObject<?> createDepartmentCampus(ModifyDepartmentCampusRequest request);

    ResponseObject<?> updateDepartmentCampus(ModifyDepartmentCampusRequest request, Long id);

    ResponseObject<?> deleteDepartmentCampus(Long id);

    ResponseObject<?> getListCampus();

    ResponseObject<?> getListHeadDepartmentCampus();

    ResponseObject<?> getListDepartmentCampus(Long id);

    ResponseObject<?> getDepartmentName(Long id);

}
