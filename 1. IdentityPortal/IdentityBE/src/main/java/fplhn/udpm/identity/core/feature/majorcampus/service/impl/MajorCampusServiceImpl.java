package fplhn.udpm.identity.core.feature.majorcampus.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.majorcampus.model.request.CreateMajorCampusRequest;
import fplhn.udpm.identity.core.feature.majorcampus.model.request.MajorCampusRequest;
import fplhn.udpm.identity.core.feature.majorcampus.model.request.UpdateMajorCampusRequest;
import fplhn.udpm.identity.core.feature.majorcampus.model.response.ModifyMajorCampusResponse;
import fplhn.udpm.identity.core.feature.majorcampus.repository.CampusMajorRepository;
import fplhn.udpm.identity.core.feature.majorcampus.repository.DepartmentCampusMajorRepository;
import fplhn.udpm.identity.core.feature.majorcampus.repository.MajorCampusExtendRepository;
import fplhn.udpm.identity.core.feature.majorcampus.repository.StaffMajorRepository;
import fplhn.udpm.identity.core.feature.majorcampus.service.MajorCampusService;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Major;
import fplhn.udpm.identity.entity.MajorCampus;
import fplhn.udpm.identity.entity.Staff;
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
@Validated
@RequiredArgsConstructor
public class MajorCampusServiceImpl implements MajorCampusService {

    private final MajorCampusExtendRepository majorCampusExtendRepository;

    private final DepartmentCampusMajorRepository departmentCampusMajorRepository;

    private final StaffMajorRepository staffMajorRepository;

    private final CampusMajorRepository majorRepository;

    @Override
    public ResponseObject<?> getAllMajorCampus(@Valid MajorCampusRequest request) {
        try {
            Pageable pageable = Helper.createPageable(request, "id");
            return ResponseObject.successForward(
                    PageableObject.of(majorCampusExtendRepository.getAllMajorCampus(request, pageable)
                    ),
                    ResponseMessage.SUCCESS.getMessage());
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseObject<?> addMajorCampus(@Valid CreateMajorCampusRequest request) {
        try {
            Optional<DepartmentCampus> departmentCampusOptional = departmentCampusMajorRepository.findById(request.getDepartmentCampusId());

            if (departmentCampusOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }

            Optional<Staff> staffOptional = staffMajorRepository.findById(request.getHeadMajorId());

            if (staffOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }

            Optional<Major> majorOptional = majorRepository.findById(request.getMajorId());

            if (majorOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }

            Optional<MajorCampus> majorCampusOptional = majorCampusExtendRepository
                    .findByMajorAndDepartmentCampus(majorOptional.get(), departmentCampusOptional.get());

            if (majorCampusOptional.isPresent()) {
                return ResponseObject.errorForward(
                        ResponseMessage.DUPLICATE.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            MajorCampus majorCampus = new MajorCampus();
            majorCampus.setDepartmentCampus(departmentCampusOptional.get());
            majorCampus.setMajor(majorOptional.get());
            majorCampus.setStaff(staffOptional.get());
            majorCampus.setEntityStatus(EntityStatus.NOT_DELETED);
            majorCampusExtendRepository.save(majorCampus);
            return ResponseObject.successForward(new ModifyMajorCampusResponse(
                            majorCampus.getMajor().getName(),
                            majorCampus.getDepartmentCampus().getCampus().getName(),
                            majorCampus.getDepartmentCampus().getDepartment().getName(),
                            majorCampus.getStaff().getFullName()
                    ),
                    ResponseMessage.CREATED.getMessage());
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> updateMajorCampus(Long majorCampusId, @Valid UpdateMajorCampusRequest request) {
        try {
            Optional<MajorCampus> majorCampusOptional = majorCampusExtendRepository.findById(majorCampusId);

            if (majorCampusOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }

            Optional<Major> majorOptional = majorRepository.findById(request.getMajorId());

            if (majorOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }

            Optional<Staff> staffOptional = staffMajorRepository.findById(request.getHeadMajorId());

            if (staffOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }

            MajorCampus majorCampus = majorCampusOptional.get();
            majorCampus.setMajor(majorOptional.get());
            majorCampus.setStaff(staffOptional.get());
            majorCampusExtendRepository.save(majorCampus);
            return ResponseObject.successForward(new ModifyMajorCampusResponse(
                    majorCampus.getMajor().getName(),
                    majorCampus.getDepartmentCampus().getCampus().getName(),
                    majorCampus.getDepartmentCampus().getDepartment().getName(),
                    majorCampus.getStaff().getFullName()
            ), ResponseMessage.UPDATED.getMessage());
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> changeStatusMajorCampus(Long majorCampusId) {
        try {
            Optional<MajorCampus> majorCampusOptional = majorCampusExtendRepository.findById(majorCampusId);

            if (majorCampusOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage(),
                        HttpStatus.NOT_FOUND
                );
            }

            MajorCampus majorCampus = majorCampusOptional.get();
            majorCampus.setEntityStatus(majorCampus.getEntityStatus() == EntityStatus.NOT_DELETED ? EntityStatus.DELETED : EntityStatus.NOT_DELETED);
            majorCampusExtendRepository.save(majorCampus);
            return ResponseObject.successForward(new ModifyMajorCampusResponse(
                    majorCampus.getMajor().getName(),
                    majorCampus.getDepartmentCampus().getCampus().getName(),
                    majorCampus.getDepartmentCampus().getDepartment().getName(),
                    majorCampus.getStaff().getFullName()
            ), ResponseMessage.UPDATED.getMessage());
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseObject<?> getDetailMajorCampus(Long majorCampusId) {
        try {
            return ResponseObject.successForward(
                    majorCampusExtendRepository.getDetailMajorCampus(majorCampusId),
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> getAllStaff() {
        try {
            return ResponseObject.successForward(
                    staffMajorRepository.getAllStaff(),
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> getAllMajor() {
        try {
            return ResponseObject.successForward(
                    majorRepository.getAllMajors(),
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
