package fplhn.udpm.identity.core.connection.service;

import fplhn.udpm.identity.core.common.ResponseObject;
import fplhn.udpm.identity.core.connection.model.request.ClientRequest;
import fplhn.udpm.identity.core.connection.model.request.GetUserDetailByUserCodeRequest;
import fplhn.udpm.identity.core.connection.model.request.DepartmentConnectorRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListDetailUserRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListStudentRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListUserByDepartmentCampusRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListUserByRoleCodeRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListUserByRoleCodeAndCampusCodeRequest;

public interface ConnectorService {

    ResponseObject getListDetailUser(GetListDetailUserRequest request);

    ResponseObject getListUserFromRoleCode(GetListUserByRoleCodeRequest request);

    ResponseObject getDetailUser(GetUserDetailByUserCodeRequest request);

    ResponseObject getListRoleCode(GetUserDetailByUserCodeRequest request);

    ResponseObject getListDepartment(DepartmentConnectorRequest request);

    ResponseObject getAllCampusByStatusDelete(ClientRequest request);

    ResponseObject getAllDepartmentByStatusDelete(ClientRequest request);

    ResponseObject getAllMajorByStatusDelete(ClientRequest request);

    ResponseObject getAllDepartmentCampusByStatusDelete(ClientRequest request);

    ResponseObject getAllMajorCampusByStatusDelete(ClientRequest request);

    ResponseObject getAllSemesterByStatusDelete(ClientRequest request);

    ResponseObject findStaffInfoByDepartmentCampusCode(GetListUserByDepartmentCampusRequest request);

    ResponseObject getAllStudentByListStudentCode(GetListStudentRequest request);

    ResponseObject getListUserByRoleCodeAndCampusCode(GetListUserByRoleCodeAndCampusCodeRequest request);

}
