package fplhn.udpm.identity.core.feature.departmentcampus.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.campus.repository.CampusExtendRepository;
import fplhn.udpm.identity.core.feature.departmentcampus.model.request.DepartmentCampusDetailRequest;
import fplhn.udpm.identity.core.feature.departmentcampus.model.request.ModifyDepartmentCampusRequest;
import fplhn.udpm.identity.core.feature.departmentcampus.model.response.ModifyDepartmentCampusSuccess;
import fplhn.udpm.identity.core.feature.departmentcampus.repository.DepartmentCampusExtendRepository;
import fplhn.udpm.identity.core.feature.departmentcampus.service.DepartmentCampusService;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.ResponseMessage;
import fplhn.udpm.identity.infrastructure.exception.RestApiException;
import fplhn.udpm.identity.repository.DepartmentRepository;
import fplhn.udpm.identity.repository.StaffRepository;
import fplhn.udpm.identity.util.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class DepartmentCampusServiceImpl implements DepartmentCampusService {

    private final DepartmentCampusExtendRepository departmentCampusExtendRepository;

    private final CampusExtendRepository campusExtendRepository;

    private final StaffRepository staffRepository;

    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseObject<?> getAllDepartmentCampus(Long id, DepartmentCampusDetailRequest request) {
        Pageable pageable = Helper.createPageable(request, "id");
        if (request.getSearchValues() == null || request.getSearchValues().length == 0) {
            return new ResponseObject<>(PageableObject.of(
                    departmentCampusExtendRepository.getAllDepartmentCampus(id, pageable)),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        }
        return new ResponseObject<>(PageableObject.of(
                departmentCampusExtendRepository.getAllDepartmentCampus(id, pageable, request)),
                HttpStatus.OK,
                ResponseMessage.SUCCESS.getMessage()
        );
    }

    @Override
    public ResponseObject<?> createDepartmentCampus(ModifyDepartmentCampusRequest request) {
        try {
            Optional<Department> departmentOptional = departmentRepository.findById(request.getDepartmentId());
            Optional<Campus> campusOptional = campusExtendRepository.findById(request.getCampusId());
            Optional<Staff> staffOptional = staffRepository.findById(request.getHeadDepartmentCampusId());

            if (departmentCampusExtendRepository.findByIdBoMonAndIdCoSoAndIdAdd(request).isPresent()) {
                return ResponseObject.errorForward(
                        ResponseMessage.DUPLICATE.getMessage(),
                        HttpStatus.BAD_REQUEST
                );
            }

            if (campusOptional.isPresent() && staffOptional.isPresent() && departmentOptional.isPresent()) {
                DepartmentCampus departmentCampus = new DepartmentCampus();
                departmentCampus.setDepartment(departmentOptional.get());
                departmentCampus.setEntityStatus(EntityStatus.NOT_DELETED);
                departmentCampus.setCampus(campusOptional.get());
                departmentCampus.setStaff(staffOptional.get());
                DepartmentCampus departmentCampusSave = departmentCampusExtendRepository.save(departmentCampus);
                return new ResponseObject<>(new ModifyDepartmentCampusSuccess(
                        departmentCampusSave.getId(),
                        departmentOptional.get().getName(),
                        campusOptional.get().getName(),
                        staffOptional.get().getFullName()
                ),
                        HttpStatus.CREATED,
                        ResponseMessage.CREATED.getMessage()
                );
            } else if (campusOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage() + " cơ sở",
                        HttpStatus.BAD_REQUEST
                );
            } else if (staffOptional.isEmpty()) {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage() + " nhân viên",
                        HttpStatus.BAD_REQUEST
                );
            } else {
                return ResponseObject.errorForward(
                        ResponseMessage.NOT_FOUND.getMessage() + " bộ môn",
                        HttpStatus.BAD_REQUEST
                );
            }
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject<?> updateDepartmentCampus(ModifyDepartmentCampusRequest request, Long id) {
        try {
            Optional<DepartmentCampus> departmentCampusOptional = departmentCampusExtendRepository.findById(id);
            if (departmentCampusOptional.isEmpty()) throw new RestApiException("Bộ môn theo cơ sở không tồn tại !");

            DepartmentCampus departmentCampus = departmentCampusOptional.get();
            Optional<Department> departmentOptional = departmentRepository.findById(request.getDepartmentId());
            Optional<Campus> campusOptional = campusExtendRepository.findById(request.getCampusId());
            Optional<Staff> staffOptional = staffRepository.findById(request.getHeadDepartmentCampusId());

            if (departmentOptional.isEmpty()) {
                throw new RestApiException("Bộ môn không tồn tại !");
            } else if (campusOptional.isEmpty()) {
                throw new RestApiException("Cơ sở không tồn tại !");
            } else if (staffOptional.isEmpty()) {
                throw new RestApiException("Nhân viên không tồn tại !");
            }

//            if (isDuplicateRecord(departmentCampus)) throw new RestApiException("Bộ môn theo cơ sở đã tồn tại !");

            departmentCampus.setStaff(staffOptional.get());
            departmentCampus.setDepartment(departmentOptional.get());
            departmentCampus.setCampus(campusOptional.get());
            departmentCampusExtendRepository.save(departmentCampus);

            return new ResponseObject<>(new ModifyDepartmentCampusSuccess(
                    departmentCampus.getId(),
                    departmentOptional.get().getName(),
                    campusOptional.get().getName(),
                    staffOptional.get().getFullName()
            ), HttpStatus.OK, "Cập nhật thành công !");
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isDuplicateRecord(DepartmentCampus departmentCampus) {
        Campus campus = departmentCampus.getCampus();
        Department department = departmentCampus.getDepartment();
        return departmentCampusExtendRepository.existsByCampusAndDepartment(campus, department);
    }

    @Override
    public ResponseObject<?> deleteDepartmentCampus(Long id) {
        Optional<DepartmentCampus> boMonTheoCoSoOptional = departmentCampusExtendRepository.findById(id);

        if (boMonTheoCoSoOptional.isPresent()) {
            if (boMonTheoCoSoOptional.get().getEntityStatus().equals(EntityStatus.NOT_DELETED)) {
                boMonTheoCoSoOptional.get().setEntityStatus(EntityStatus.DELETED);
            } else {
                boMonTheoCoSoOptional.get().setEntityStatus(EntityStatus.NOT_DELETED);
            }
            departmentCampusExtendRepository.save(boMonTheoCoSoOptional.get());
            return ResponseObject.successForward(
                    HttpStatus.OK,
                    ResponseMessage.UPDATED.getMessage()
            );
        } else {
            return ResponseObject.errorForward(
                    ResponseMessage.NOT_FOUND.getMessage(),
                    HttpStatus.NOT_FOUND
            );
        }
    }

    @Override
    public ResponseObject<?> getListCampus() {
        try {
            return new ResponseObject<>(
                    departmentCampusExtendRepository.getListCoSo(),
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

    @Override
    public ResponseObject<?> getListHeadDepartmentCampus() {
        try {
            return new ResponseObject<>(
                    departmentCampusExtendRepository.getListNhanVien(),
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

    @Override
    public ResponseObject<?> getListDepartmentCampus(Long id) {
        try {
            return new ResponseObject<>(
                    departmentCampusExtendRepository.getListBoMonTheoCoSo(id),
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

    @Override
    public ResponseObject<?> getDepartmentName(Long id) {
        try {
            return new ResponseObject<>(
                    departmentCampusExtendRepository.getTenBoMon(id),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage());
        } catch (Exception e) {
            return ResponseObject.errorForward(
                    ResponseMessage.INTERNAL_SERVER_ERROR.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }
}
