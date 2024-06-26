package fplhn.udpm.identity.core.feature.department.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.department.model.request.CreateDepartmentRequest;
import fplhn.udpm.identity.core.feature.department.model.request.DepartmentPaginationRequest;
import fplhn.udpm.identity.core.feature.department.model.request.UpdateDepartmentRequest;

public interface DepartmentService {

    ResponseObject<?> getAllDepartmentPagination(DepartmentPaginationRequest request);

    ResponseObject<?> createDepartment(CreateDepartmentRequest request);

    ResponseObject<?> updateDepartment(UpdateDepartmentRequest request, Long id);

    ResponseObject<?> deleteDepartment(Long id);

    ResponseObject<?> getListDepartment();

}
