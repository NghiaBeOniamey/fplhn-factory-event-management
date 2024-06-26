package fplhn.udpm.identity.core.feature.staff.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.staff.model.request.CreateStaffRequest;
import fplhn.udpm.identity.core.feature.staff.model.request.PaginationStaffRequest;
import fplhn.udpm.identity.core.feature.staff.model.request.UpdateStaffRequest;

public interface StaffService {

    ResponseObject<?> getAllStaff(PaginationStaffRequest request);

    ResponseObject<?> createStaff(CreateStaffRequest request);

    ResponseObject<?> updateStaff(Long id, UpdateStaffRequest request);

    ResponseObject<?> updateStaffStatus(Long id);

    ResponseObject<?> getDetailStaff(Long id);

}
