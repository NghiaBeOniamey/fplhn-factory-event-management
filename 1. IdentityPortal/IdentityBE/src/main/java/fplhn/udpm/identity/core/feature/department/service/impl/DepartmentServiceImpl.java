package fplhn.udpm.identity.core.feature.department.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.department.model.request.CreateDepartmentRequest;
import fplhn.udpm.identity.core.feature.department.model.request.DepartmentPaginationRequest;
import fplhn.udpm.identity.core.feature.department.model.request.UpdateDepartmentRequest;
import fplhn.udpm.identity.core.feature.department.repository.DepartmentExtendRepository;
import fplhn.udpm.identity.core.feature.department.service.DepartmentService;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.ResponseMessage;
import fplhn.udpm.identity.util.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentExtendRepository departmentExtendRepository;

    @Override
    public ResponseObject<?> getAllDepartmentPagination(DepartmentPaginationRequest request) {
        try {
            Pageable pageable = Helper.createPageable(request, "departmentId");
            if (request.getSearchValues() == null || request.getSearchValues().length == 0) {
                return new ResponseObject<>(
                        PageableObject.of(departmentExtendRepository.getAllDepartment(pageable)),
                        HttpStatus.OK,
                        ResponseMessage.SUCCESS.getMessage()
                );
            }
            return new ResponseObject<>(
                    PageableObject.of(departmentExtendRepository.getAllDepartmentFilter(pageable, request)),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public ResponseObject<?> createDepartment(CreateDepartmentRequest request) {
        if (request.getDepartmentName().length() > 255) {
            return ResponseObject.errorForward(
                    ResponseMessage.STRING_TOO_LONG.getMessage(), HttpStatus.BAD_REQUEST
            );
        }
        request.setDepartmentName(request.getDepartmentName().replaceAll("\\s+", " "));
        request.setDepartmentCode(request.getDepartmentCode().replaceAll("\\s+", " "));
        boolean existsByCode = departmentExtendRepository.existsByCode(request.getDepartmentCode().trim());
        if (existsByCode) {
            return ResponseObject.errorForward(ResponseMessage.DUPLICATE.getMessage(), HttpStatus.BAD_REQUEST);
        } else {
            Department department = new Department();
            department.setEntityStatus(EntityStatus.NOT_DELETED);
            department.setName(request.getDepartmentName().trim());
            department.setCode(request.getDepartmentCode().trim());
            return new ResponseObject<>(
                    departmentExtendRepository.save(department),
                    HttpStatus.CREATED,
                    ResponseMessage.SUCCESS.getMessage()
            );
        }
    }

    @Override
    public ResponseObject<?> updateDepartment(UpdateDepartmentRequest request, Long id) {
        if (request.getDepartmentName().length() > 255) {
            return ResponseObject.errorForward(ResponseMessage.STRING_TOO_LONG.getMessage(), HttpStatus.BAD_REQUEST);
        }
        request.setDepartmentName(request.getDepartmentName().replaceAll("\\s+", " "));
        request.setDepartmentCode(request.getDepartmentCode().replaceAll("\\s+", " "));

        Optional<Department> departmentOptional = departmentExtendRepository.findById(id);
        if (departmentOptional.isEmpty()) {
            return ResponseObject.errorForward(
                    ResponseMessage.NOT_FOUND.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (!departmentOptional.get().getCode().trim().equalsIgnoreCase(request.getDepartmentCode().trim())) {
            if (departmentExtendRepository.existsByCode(request.getDepartmentCode().trim())) {
                return ResponseObject.errorForward(
                        ResponseMessage.DUPLICATE.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        departmentOptional.get().setName(request.getDepartmentName().trim());
        departmentOptional.get().setCode(request.getDepartmentCode().trim());
        return new ResponseObject<>(
                departmentExtendRepository.save(departmentOptional.get()),
                HttpStatus.OK,
                ResponseMessage.SUCCESS.getMessage()
        );

    }

    @Override
    public ResponseObject<?> deleteDepartment(Long id) {
        Department department = departmentExtendRepository.findById(id)
                .map(dp -> {
                    if (dp.getEntityStatus() == EntityStatus.DELETED) {
                        dp.setEntityStatus(EntityStatus.NOT_DELETED);
                    } else {
                        dp.setEntityStatus(EntityStatus.DELETED);
                    }
                    return departmentExtendRepository.save(dp);
                }).orElse(null);
        if (department != null) {
            return new ResponseObject<>(
                    department,
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } else {
            return ResponseObject.errorForward(
                    ResponseMessage.NOT_FOUND.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @Override
    public ResponseObject<?> getListDepartment() {
        try {
            return new ResponseObject<>(
                    departmentExtendRepository.getListAllDepartment(),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

}
