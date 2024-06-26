package fplhn.udpm.identity.core.connection.controller;

import fplhn.udpm.identity.core.connection.model.request.ClientRequest;
import fplhn.udpm.identity.core.connection.model.request.DepartmentConnectorRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListDetailUserRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListStudentRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListUserByDepartmentCampusRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListUserByRoleCodeAndCampusCodeRequest;
import fplhn.udpm.identity.core.connection.model.request.GetListUserByRoleCodeRequest;
import fplhn.udpm.identity.core.connection.model.request.GetUserDetailByUserCodeRequest;
import fplhn.udpm.identity.core.connection.model.response.CampusConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.DepartmentCampusConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.DepartmentConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.DepartmentOldResponse;
import fplhn.udpm.identity.core.connection.model.response.DetailUserResponseStaff;
import fplhn.udpm.identity.core.connection.model.response.MajorCampusConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.MajorConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.RolesResponse;
import fplhn.udpm.identity.core.connection.model.response.SemesterConnectionResponse;
import fplhn.udpm.identity.core.connection.model.response.StaffResponse;
import fplhn.udpm.identity.core.connection.model.response.StudentResponse;
import fplhn.udpm.identity.core.connection.service.ConnectorService;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import fplhn.udpm.identity.util.Helper;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.API_CONNECTOR_PREFIX)
@CrossOrigin("*")
@Slf4j
public class ConnectorController {

    private final ConnectorService connectorService;

    /**
     * Get list detail user
     *
     * @param request GetListDetailUserRequest
     * @return List<DetailUserResponseStaff>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get list detail user",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DetailUserResponseStaff.class))
            )
    )
    @PostMapping("/get-detail-users")
    public ResponseEntity<?> GetDetailUsers(@Valid @RequestBody GetListDetailUserRequest request) {
        return Helper.createResponseEntity(connectorService.getListDetailUser(request));
    }

    /**
     * Get list user from role code
     *
     * @param request GetListUserFromRoleCodeRequest
     * @return List<DetailUserResponseStaff>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get list user from role code",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DetailUserResponseStaff.class))
            )
    )
    @PostMapping("/get-list-user-by-role-code")
    public ResponseEntity<?> GetListUserFromRoleCode(@Valid @RequestBody GetListUserByRoleCodeRequest request) {
        return Helper.createResponseEntity(connectorService.getListUserFromRoleCode(request));
    }

    /**
     * Get detail user
     *
     * @param request CommonUserRoleRequest
     * @return DetailUserResponseStaff
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get detail user",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DetailUserResponseStaff.class)
            )
    )
    @PostMapping("/get-user-by-id")
    public ResponseEntity<?> findUserById(@Valid @RequestBody GetUserDetailByUserCodeRequest request) {
        return Helper.createResponseEntity(connectorService.getDetailUser(request));
    }

    /**
     * Get roles of user
     *
     * @param request CommonUserRoleRequest
     * @return List<RolesResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get roles of user",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = RolesResponse.class))
            )
    )
    @PostMapping("/get-roles-of-user")
    public ResponseEntity<?> findRolesOfUserId(@Valid @RequestBody GetUserDetailByUserCodeRequest request) {
        return Helper.createResponseEntity(connectorService.getListRoleCode(request));
    }

    /**
     * Get departments
     *
     * @param request DepartmentConnectorRequest
     * @return List<DepartmentOldResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get departments",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DepartmentOldResponse.class))
            )
    )
    @PostMapping("/get-departments")
    public ResponseEntity<?> getDepartments(@Valid @RequestBody DepartmentConnectorRequest request) {
        return Helper.createResponseEntity(connectorService.getListDepartment(request));
    }

    /**
     * Get campus by status delete
     *
     * @param request ClientRequest
     * @return List<CampusConnectionResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get campus by status delete",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = CampusConnectionResponse.class))
            )
    )
    @PostMapping("/get-campus-by-status")
    public ResponseEntity<?> getCampusByStatusDelete(@Valid @RequestBody ClientRequest request) {
        return Helper.createResponseEntity(connectorService.getAllCampusByStatusDelete(request));
    }

    /**
     * Get departments by status delete
     *
     * @param request ClientRequest
     * @return List<DepartmentConnectionResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get departments by status delete",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DepartmentConnectionResponse.class))
            )
    )
    @PostMapping("/get-departments-by-status")
    public ResponseEntity<?> getDepartmentsByStatusDelete(@Valid @RequestBody ClientRequest request) {
        return Helper.createResponseEntity(connectorService.getAllDepartmentByStatusDelete(request));
    }

    /**
     * Get majors by status delete
     *
     * @param request ClientRequest
     * @return List<MajorConnectionResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get majors by status delete",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MajorConnectionResponse.class))
            )
    )
    @PostMapping("/get-majors-by-status")
    public ResponseEntity<?> getMajorsByStatus(@Valid @RequestBody ClientRequest request) {
        return Helper.createResponseEntity(connectorService.getAllMajorByStatusDelete(request));
    }

    /**
     * Get department campus by status delete
     *
     * @param request ClientRequest
     * @return List<DepartmentCampusConnectionResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get department campus by status delete",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = DepartmentCampusConnectionResponse.class))
            )
    )
    @PostMapping("/get-department-campus-by-status")
    public ResponseEntity<?> getDepartmentsCampusByStatus(@Valid @RequestBody ClientRequest request) {
        return Helper.createResponseEntity(connectorService.getAllDepartmentCampusByStatusDelete(request));
    }

    /**
     * Get major campus by status delete
     *
     * @param request ClientRequest
     * @return List<MajorCampusConnectionResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get major campus by status delete",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = MajorCampusConnectionResponse.class))
            )
    )
    @PostMapping("/get-major-campus-by-status")
    public ResponseEntity<?> getDepartments(@Valid @RequestBody ClientRequest request) {
        return Helper.createResponseEntity(connectorService.getAllMajorCampusByStatusDelete(request));
    }

    /**
     * Get semesters by status delete
     *
     * @param request ClientRequest
     * @return List<SemesterConnectionResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get semesters by status delete",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = SemesterConnectionResponse.class))
            )
    )
    @PostMapping("/get-semesters-by-status")
    public ResponseEntity<?> getSemesters(@Valid @RequestBody ClientRequest request) {
        return Helper.createResponseEntity(connectorService.getAllSemesterByStatusDelete(request));
    }

    /**
     * Get users by department campus
     *
     * @param request GetListUserByDepartmentCampusRequest
     * @return List<StaffResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get users by department campus",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = StaffResponse.class))
            )
    )
    @PostMapping("/get-users-by-department-campus")
    public ResponseEntity<?> getUsersByDepartmentCampus(@Valid @RequestBody GetListUserByDepartmentCampusRequest request) {
        return Helper.createResponseEntity(connectorService.findStaffInfoByDepartmentCampusCode(request));
    }

    /**
     * Get students
     *
     * @param request GetListStudentRequest
     * @return List<StaffResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get students",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = StudentResponse.class))
            )
    )
    @PostMapping("/get-students")
    public ResponseEntity<?> getStudents(@Valid @RequestBody GetListStudentRequest request) {
        return Helper.createResponseEntity(connectorService.getAllStudentByListStudentCode(request));
    }

    /**
     * Get list user by role code and campus code
     *
     * @param request ListUserByRoleCodeAndCampusCodeRequest
     * @return List<StaffResponse>
     */
    @ApiResponse(
            responseCode = "200",
            description = "Get list user by role code and campus code",
            content = @Content(
                    mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = StaffResponse.class))
            )
    )
    @PostMapping("/get-list-user-by-role-code-and-campus-code")
    public ResponseEntity<?> getListUserByRoleCodeAndCampusCode(@Valid @RequestBody GetListUserByRoleCodeAndCampusCodeRequest request) {
        return Helper.createResponseEntity(connectorService.getListUserByRoleCodeAndCampusCode(request));
    }

}
