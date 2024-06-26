package fplhn.udpm.identity.core.connection.service.impl;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.connection.model.request.ClientRequest;
import fplhn.udpm.identity.core.connection.model.request.GetUserDetailByUserCodeRequest;
import fplhn.udpm.identity.core.connection.model.request.DepartmentConnectorRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListDetailUserRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListStudentRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListUserByDepartmentCampusRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListUserByRoleCodeRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListUserByRoleCodeAndCampusCodeRequest;
import fplhn.udpm.identity.core.connection.model.response.CampusConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.DepartmentCampusConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.DepartmentConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.DepartmentOldResponse;
import fplhn.udpm.identity.core.connection.model.response.DetailUserResponseStaff;
import fplhn.udpm.identity.core.connection.model.response.MajorCampusConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.MajorConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.RolesResponse;
import fplhn.udpm.identity.core.connection.model.response.SemesterConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.StudentResponse;
import fplhn.udpm.identity.core.connection.repository.CampusConnectorRepository;
import fplhn.udpm.identity.core.connection.repository.ClientConnectorRepository;
import fplhn.udpm.identity.core.connection.repository.DepartmentCampusConnectorRepository;
import fplhn.udpm.identity.core.connection.repository.DepartmentConnectorRepository;
import fplhn.udpm.identity.core.connection.repository.MajorCampusConnectionRepository;
import fplhn.udpm.identity.core.connection.repository.MajorConnectionRepository;
import fplhn.udpm.identity.core.connection.repository.SemesterConnectionRepository;
import fplhn.udpm.identity.core.connection.repository.StaffConnectorRepository;
import fplhn.udpm.identity.core.connection.repository.StudentConnectionRepository;
import fplhn.udpm.identity.core.connection.service.ConnectorService;
import fplhn.udpm.identity.entity.Role;
import fplhn.udpm.identity.entity.Staff;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static fplhn.udpm.identity.infrastructure.constant.MessageConstant.CALL_API_FAIL;
import static fplhn.udpm.identity.infrastructure.constant.MessageConstant.CALL_API_SUCCESS;
import static fplhn.udpm.identity.infrastructure.constant.MessageConstant.CLIENT_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectorServiceImpl implements ConnectorService {

    private final ClientConnectorRepository clientConnectorRepository;

    private final StaffConnectorRepository staffConnectorRepository;

    private final CampusConnectorRepository campusConnectorRepository;

    private final DepartmentConnectorRepository departmentConnectorRepository;

    private final MajorConnectionRepository majorConnectionRepository;

    private final DepartmentCampusConnectorRepository departmentCampusConnectorRepository;

    private final MajorCampusConnectionRepository majorCampusConnectionRepository;

    private final SemesterConnectionRepository semesterConnectionRepository;

    private final StudentConnectionRepository studentConnectionRepository;

    @Override
    public ResponseObject getListDetailUser(GetListDetailUserRequest request) {
        try {
            boolean checkClient = hadRegisterClient(request.getClientId(), request.getClientSecret());
            if (!checkClient) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            List<String> userId = request.getUserCodes();
            List<Staff> listStaffValid = new ArrayList<>();
            for (String userCode : userId) {
                Optional<Staff> staffOptional = staffConnectorRepository.findByStaffCode(userCode);
                staffOptional.ifPresent(listStaffValid::add);
            }
            return ResponseObject.successForward(getResponseObject(listStaffValid), CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getListUserFromRoleCode(GetListUserByRoleCodeRequest request) {
        try {
            boolean checkClient = hadRegisterClient(request.getClientId(), request.getClientSecret());
            if (!checkClient) return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);

            String roleCode = request.getRoleCode();
            String campusCode = request.getCampusCode();
            List<String> subjectCode = request.getListDepartmentCode();

            if (subjectCode == null || subjectCode.isEmpty()) {
                List<Staff> listStaff = staffConnectorRepository.findByRoleCodeAndCampusCode(roleCode, campusCode);
                List<DetailUserResponseStaff> listDetailUserResponseStaff = new ArrayList<>();
                for (Staff staff : listStaff) {
                    DetailUserResponseStaff detailUserResponseStaff = createDetailUserResponseStaff(staff);
                    listDetailUserResponseStaff.add(detailUserResponseStaff);
                }
                return ResponseObject.successForward(listDetailUserResponseStaff, CALL_API_SUCCESS);
            }

            List<Staff> listStaff = staffConnectorRepository.findByRoleCodeDepartmentCodesAndCampusCode(roleCode, subjectCode, campusCode);
            List<DetailUserResponseStaff> listDetailUserResponseStaff = new ArrayList<>();
            for (Staff staff : listStaff) {
                DetailUserResponseStaff detailUserResponseStaff = createDetailUserResponseStaff(staff);
                listDetailUserResponseStaff.add(detailUserResponseStaff);
            }
            return ResponseObject.successForward(listDetailUserResponseStaff, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getDetailUser(GetUserDetailByUserCodeRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            String userCode = request.getUserCode();
            Optional<Staff> staffOptional =
                    staffConnectorRepository.
                            findByStaffCode(userCode);
            if (staffOptional.isEmpty()) {
                return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.NOT_FOUND);
            }
            Staff staff = staffOptional.get();
            return ResponseObject.successForward(createDetailUserResponseStaff(staff), CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getListRoleCode(GetUserDetailByUserCodeRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            List<Role> listRoleCode = staffConnectorRepository.findRoleCodeByStaffId(request.getUserCode());
            List<RolesResponse> listRoleCodeResponse = new ArrayList<>();
            for (Role role : listRoleCode) {
                RolesResponse rolesResponse = new RolesResponse();
                rolesResponse.setId(role.getId());
                rolesResponse.setRoleCode(role.getCode());
                rolesResponse.setRoleName(role.getName());
                listRoleCodeResponse.add(rolesResponse);
            }
            return ResponseObject.successForward(listRoleCodeResponse, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getListDepartment(DepartmentConnectorRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            String campusCode = request.getCampusCode();
            List<DepartmentOldResponse> listDepartmentResponse = departmentCampusConnectorRepository.getDepartmentByCampusCode(campusCode);
            return ResponseObject.successForward(listDepartmentResponse, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getAllCampusByStatusDelete(ClientRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            List<CampusConnectionResponse> campusConnectionResponses = campusConnectorRepository.getAllByDeleteStatus();
            return ResponseObject.successForward(campusConnectionResponses, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getAllDepartmentByStatusDelete(ClientRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            List<DepartmentConnectionResponse> departmentConnectionResponses = departmentConnectorRepository.getAllByDeleteStatus();
            return ResponseObject.successForward(departmentConnectionResponses, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getAllMajorByStatusDelete(ClientRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            List<MajorConnectionResponse> majorConnectionResponses = majorConnectionRepository.getAllByDeleteStatus();
            return ResponseObject.successForward(majorConnectionResponses, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getAllDepartmentCampusByStatusDelete(ClientRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            List<DepartmentCampusConnectionResponse> departmentCampusConnectionResponses = departmentCampusConnectorRepository.getAllByDeleteStatus();
            return ResponseObject.successForward(departmentCampusConnectionResponses, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getAllMajorCampusByStatusDelete(ClientRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            List<MajorCampusConnectionResponse> majorCampusConnectionResponses = majorCampusConnectionRepository.getAllByDeleteStatus();
            return ResponseObject.successForward(majorCampusConnectionResponses, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getAllSemesterByStatusDelete(ClientRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            List<SemesterConnectionResponse> semesterConnectionResponses = semesterConnectionRepository.findAllSemesterConnection();
            return ResponseObject.successForward(semesterConnectionResponses, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject findStaffInfoByDepartmentCampusCode(GetListUserByDepartmentCampusRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            String campusCode = request.getCampusCode();
            List<String> subjectCode = request.getListDepartmentCode();
            return ResponseObject.successForward(staffConnectorRepository.findByStaffInfo(subjectCode, campusCode), CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getAllStudentByListStudentCode(GetListStudentRequest request) {
        try {
            if (!hadRegisterClient(request.getClientId(), request.getClientSecret())) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }
            List<StudentResponse> studentResponses = studentConnectionRepository.findByStudentCodes(request.getStudentCodes());
            return ResponseObject.successForward(studentResponses, CALL_API_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseObject getListUserByRoleCodeAndCampusCode(GetListUserByRoleCodeAndCampusCodeRequest request) {
        try {
            boolean checkClient = hadRegisterClient(request.getClientId(), request.getClientSecret());
            if (!checkClient) {
                return ResponseObject.errorForward(CLIENT_NOT_FOUND, HttpStatus.NOT_ACCEPTABLE);
            }

            String roleCode = request.getRoleCode();
            String campusCode = request.getCampusCode();
            List<Staff> listStaff = staffConnectorRepository.findByRoleCodeAndCampusCode(roleCode, campusCode);
            List<DetailUserResponseStaff> listDetailUserResponseStaff = new ArrayList<>();
            for (Staff staff : listStaff) {
                DetailUserResponseStaff detailUserResponseStaff = createDetailUserResponseStaff(staff);
                listDetailUserResponseStaff.add(detailUserResponseStaff);
            }
            return ResponseObject.successForward(listDetailUserResponseStaff, CALL_API_SUCCESS);
        } catch (Exception e) {
            return ResponseObject.errorForward(CALL_API_FAIL, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    private boolean hadRegisterClient(String clientId, String clientSecret) {
        boolean res = clientConnectorRepository.findByClientIdAndClientSecret(clientId, clientSecret).isEmpty();
        return !res;
    }

    private List<DetailUserResponseStaff> getResponseObject(List<Staff> listStaff) {
        try {
            List<DetailUserResponseStaff> listDetailUserResponseStaff = new ArrayList<>();
            for (Staff staff : listStaff) {
                DetailUserResponseStaff detailUserResponseStaff = createDetailUserResponseStaff(staff);
                listDetailUserResponseStaff.add(detailUserResponseStaff);
            }
            return listDetailUserResponseStaff;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private DetailUserResponseStaff createDetailUserResponseStaff(Staff staff) {
        DetailUserResponseStaff detailUserResponseStaff = new DetailUserResponseStaff();
        detailUserResponseStaff.setId(staff.getStaffCode());
        detailUserResponseStaff.setName(staff.getFullName());
        detailUserResponseStaff.setUserCode(staff.getStaffCode());
        detailUserResponseStaff.setUserName(staff.getStaffCode());
        detailUserResponseStaff.setEmailFPT(staff.getAccountFPT());
        detailUserResponseStaff.setEmailFE(staff.getAccountFE());
        detailUserResponseStaff.setPicture(staff.getAvatar());
        detailUserResponseStaff.setUserSubjectCode(staff.getDepartmentCampus().getDepartment().getCode());
        detailUserResponseStaff.setUserTrainingFacilityCode(staff.getDepartmentCampus().getCampus().getCode());
        return detailUserResponseStaff;
    }

}
