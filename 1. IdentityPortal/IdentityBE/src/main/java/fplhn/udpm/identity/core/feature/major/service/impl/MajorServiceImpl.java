package fplhn.udpm.identity.core.feature.major.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.major.model.request.CreateMajorRequest;
import fplhn.udpm.identity.core.feature.major.model.request.MajorRequest;
import fplhn.udpm.identity.core.feature.major.model.request.UpdateMajorRequest;
import fplhn.udpm.identity.core.feature.major.repository.DepartmentMajorRepository;
import fplhn.udpm.identity.core.feature.major.repository.MajorExtendRepository;
import fplhn.udpm.identity.core.feature.major.service.MajorService;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.entity.Major;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.ResponseMessage;
import fplhn.udpm.identity.util.Helper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class MajorServiceImpl implements MajorService {

    private final MajorExtendRepository majorExtendRepository;

    private final DepartmentMajorRepository departmentRepository;

    @Override
    public ResponseObject<?> findAllMajor(@Valid MajorRequest request) {
        try {
            Pageable pageable = Helper.createPageable(request, "majorId");
            return ResponseObject.successForward(
                    PageableObject.of(majorExtendRepository.findAllMajor(pageable, request)),
                    ResponseMessage.SUCCESS.getMessage());
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> addMajor(@Valid CreateMajorRequest request) {
        try {
            Optional<Department> departmentOptional = departmentRepository.findById(request.getDepartmentId());

            if (departmentOptional.isEmpty()) {
                return ResponseObject.errorForward(ResponseMessage.NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            }

            Optional<Major> majorOptional = majorExtendRepository.findByCode(request.getMajorCode());
            if (majorOptional.isPresent()) {
                return ResponseObject.errorForward(
                        ResponseMessage.DUPLICATE.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            Major major = new Major();
            major.setCode(request.getMajorCode());
            major.setName(request.getMajorName());
            major.setEntityStatus(EntityStatus.NOT_DELETED);
            major.setDepartment(departmentOptional.get());
            return new ResponseObject<>(
                    majorExtendRepository.save(major),
                    HttpStatus.CREATED,
                    ResponseMessage.CREATED.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> updateMajor(@Valid UpdateMajorRequest request, Long id) {
        try {
            Optional<Major> majorOptional = majorExtendRepository.findById(id);
            if (majorOptional.isEmpty()) {
                return ResponseObject.errorForward(ResponseMessage.NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            }

            Optional<Major> majorCodeOptional = majorExtendRepository.findByCode(request.getMajorCode());
            if (majorCodeOptional.isPresent() && !majorCodeOptional.get().getId().equals(id)) {
                return ResponseObject.errorForward(
                        ResponseMessage.DUPLICATE.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            Major major = majorOptional.get();
            major.setCode(request.getMajorCode());
            major.setName(request.getMajorName());
            return ResponseObject.successForward(
                    majorExtendRepository.save(major),
                    ResponseMessage.UPDATED.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> changeStatus(Long majorId) {
        try {
            Optional<Major> majorOptional = majorExtendRepository.findById(majorId);
            if (majorOptional.isEmpty()) {
                return ResponseObject.errorForward(ResponseMessage.NOT_FOUND.getMessage(), HttpStatus.NOT_FOUND);
            }
            Major major = majorOptional.get();
            major.setEntityStatus(major.getEntityStatus() == EntityStatus.NOT_DELETED ? EntityStatus.DELETED : EntityStatus.NOT_DELETED);
            return ResponseObject.successForward(majorExtendRepository.save(major), ResponseMessage.UPDATED.getMessage());
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> findDetailMajor(Long majorId) {
        try {
            return ResponseObject.successForward(majorExtendRepository.findByMajorId(majorId), "Lấy thông tin chuyên ngành thành công");
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
