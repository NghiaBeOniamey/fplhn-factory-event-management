package fplhn.udpm.identity.core.feature.staff.service.impl;

import fplhn.udpm.identity.core.common.PageableObject;
import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.feature.staff.model.request.CreateStaffRequest;
import fplhn.udpm.identity.core.feature.staff.model.request.PaginationStaffRequest;
import fplhn.udpm.identity.core.feature.staff.model.request.UpdateStaffRequest;
import fplhn.udpm.identity.core.feature.staff.model.response.ModifyStaffResponse;
import fplhn.udpm.identity.core.feature.staff.repository.CampusStaffRepository;
import fplhn.udpm.identity.core.feature.staff.repository.DepartmentCampusStaffRepository;
import fplhn.udpm.identity.core.feature.staff.repository.DepartmentStaffRepository;
import fplhn.udpm.identity.core.feature.staff.repository.StaffCustomRepository;
import fplhn.udpm.identity.core.feature.staff.service.StaffService;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.config.websocket.service.WebSocketService;
import fplhn.udpm.identity.infrastructure.constant.EntityStatus;
import fplhn.udpm.identity.infrastructure.constant.ResponseMessage;
import fplhn.udpm.identity.infrastructure.constant.WebSocketCommand;
import fplhn.udpm.identity.infrastructure.exception.RestApiException;
import fplhn.udpm.identity.util.Helper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService {

    private final StaffCustomRepository staffCustomRepository;

    private final CampusStaffRepository campusStaffRepository;

    private final DepartmentCampusStaffRepository departmentCampusStaffRepository;

    private final DepartmentStaffRepository departmentStaffRepository;

    private final WebSocketService webSocketService;

    @Override
    public ResponseObject<?> getAllStaff(PaginationStaffRequest request) {
        try {
            Pageable pageable = Helper.createPageable(request, "id");
            return new ResponseObject<>(
                    PageableObject.of(staffCustomRepository.findAllNhanVien(request, pageable)),
                    HttpStatus.OK,
                    ResponseMessage.SUCCESS.getMessage()
            );
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseObject<?> createStaff(CreateStaffRequest request) {
        try {
            Staff staff = createNewStaff(request);
            checkExistingStaff(request);
            assignCampusAndDepartment(request, staff);
            staffCustomRepository.save(staff);
            return createSuccessResponse(staff, ResponseMessage.CREATED.getMessage());
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseObject<?> updateStaff(Long id, UpdateStaffRequest request) {
        try {
            return staffCustomRepository.findById(id)
                    .map(staff -> {
                        updateStaffCampusAndDepartment(request, staff);
                        updateStaffInfo(request, staff);
                        checkEmailExistence(request, staff);
                        staffCustomRepository.save(staff);
                        return createSuccessResponse(staff, ResponseMessage.UPDATED.getMessage());
                    })
                    .orElseGet(() -> ResponseObject.errorForward(
                            ResponseMessage.NOT_FOUND.getMessage(),
                            HttpStatus.BAD_REQUEST
                    ));
        } catch (Exception e) {
            return ResponseObject.errorForward(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseObject<?> updateStaffStatus(Long id) {
        Optional<Staff> nhanVienOptional = staffCustomRepository.findById(id);
        if (nhanVienOptional.isEmpty()) {
            return new ResponseObject<>(
                    null,
                    HttpStatus.BAD_REQUEST,
                    ResponseMessage.NOT_FOUND.getMessage());
        }
        Staff staff = nhanVienOptional.get();
        staff.setEntityStatus(
                staff.getEntityStatus() == EntityStatus.NOT_DELETED ? EntityStatus.DELETED : EntityStatus.NOT_DELETED
        );
        Staff staffDeleted = staffCustomRepository.save(staff);
        webSocketService.broadcastMessage(WebSocketCommand.getChangeStatusUser(id));
        return new ResponseObject<>(
                new ModifyStaffResponse(
                        staffDeleted.getFullName(),
                        staffDeleted.getStaffCode(),
                        staffDeleted.getAccountFE(),
                        staffDeleted.getPhoneNumber(),
                        staffDeleted.getAccountFPT()
                ),
                HttpStatus.OK,
                ResponseMessage.UPDATED.getMessage()
        );
    }

    @Override
    public ResponseObject<?> getDetailStaff(Long id) {
        return Optional.ofNullable(staffCustomRepository.findDetailById(id))
                .map(detailStaffResponse -> new ResponseObject<>(
                        detailStaffResponse,
                        HttpStatus.OK,
                        ResponseMessage.SUCCESS.getMessage())
                )
                .orElseGet(() -> new ResponseObject<>(
                                null,
                                HttpStatus.BAD_REQUEST,
                                ResponseMessage.NOT_FOUND.getMessage()
                        )
                );
    }

    private Staff createNewStaff(CreateStaffRequest request) {
        Staff staff = new Staff();
        staff.setStaffCode(request.getStaffCode());
        staff.setFullName(request.getStaffName());
        staff.setAccountFE(request.getEmailFe());
        staff.setAccountFPT(request.getEmailFpt());
        staff.setPhoneNumber(request.getPhoneNumber());
        staff.setEntityStatus(EntityStatus.NOT_DELETED);
        return staff;
    }

    private void checkExistingStaff(CreateStaffRequest request) {
        if (staffCustomRepository.existsByStaffCode(request.getStaffCode())) {
            throw new RestApiException("Mã nhân viên đã tồn tại");
        }
        if (staffCustomRepository.existsByAccountFE(request.getEmailFe())) {
            throw new RestApiException("Email FE đã tồn tại");
        }
        if (staffCustomRepository.existsByAccountFPT(request.getEmailFpt())) {
            throw new RestApiException("Email FPT đã tồn tại");
        }
    }

    private void assignCampusAndDepartment(CreateStaffRequest request, Staff staff) {
        if (request.getCampusId() != null) {
            Campus campus = campusStaffRepository.findById(request.getCampusId())
                    .orElseThrow(() -> new RestApiException("Cơ sở không tồn tại"));
            staff.setCampus(campus);
            if (request.getDepartmentId() != null) {
                Department department = departmentStaffRepository.findById(request.getDepartmentId())
                        .orElseThrow(() -> new RestApiException("Bộ môn không tồn tại"));
                DepartmentCampus departmentCampus = departmentCampusStaffRepository.findByCampusAndDepartment(campus, department)
                        .orElseThrow(() -> new RestApiException("Bộ môn không thuộc cơ sở"));
                staff.setDepartmentCampus(departmentCampus);
            }
        }
    }

    private ResponseObject<?> createSuccessResponse(Staff staff, String message) {
        return new ResponseObject<>(new ModifyStaffResponse(
                staff.getFullName(),
                staff.getStaffCode(),
                staff.getAccountFE(),
                staff.getPhoneNumber(),
                staff.getAccountFPT()
        ), HttpStatus.OK, message);
    }

    private void updateStaffCampusAndDepartment(UpdateStaffRequest request, Staff staff) {
        if (request.getCampusId() != null) {
            Campus campus = campusStaffRepository.findById(request.getCampusId())
                    .orElseThrow(() -> new RestApiException("Cơ sở không tồn tại"));
            staff.setCampus(campus);
            if (request.getDepartmentId() != null) {
                Department department = departmentStaffRepository.findById(request.getDepartmentId())
                        .orElseThrow(() -> new RestApiException("Bộ môn không tồn tại"));
                DepartmentCampus departmentCampus = departmentCampusStaffRepository.findByCampusAndDepartment(campus, department)
                        .orElseThrow(() -> new RestApiException("Bộ môn không thuộc cơ sở"));
                staff.setDepartmentCampus(departmentCampus);
            } else {
                staff.setDepartmentCampus(null);
            }
        } else {
            staff.setCampus(null);
        }
    }

    private void updateStaffInfo(UpdateStaffRequest request, Staff staff) {
        staff.setFullName(request.getStaffName());
        staff.setAccountFE(request.getEmailFe());
        staff.setAccountFPT(request.getEmailFpt());
        if (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) {
            staff.setPhoneNumber(request.getPhoneNumber());
        } else {
            staff.setPhoneNumber(null);
        }
    }

    private void checkEmailExistence(UpdateStaffRequest request, Staff staff) {
        if (staffCustomRepository.existsByAccountFE(request.getEmailFe()) && !staff.getAccountFE().equals(request.getEmailFe())) {
            throw new RestApiException("Email FE đã tồn tại");
        }
        if (staffCustomRepository.existsByAccountFPT(request.getEmailFpt()) && !staff.getAccountFPT().equals(request.getEmailFpt())) {
            throw new RestApiException("Email FPT đã tồn tại");
        }
    }

}
