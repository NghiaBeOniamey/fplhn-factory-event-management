package com.portalevent.util;

import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHOCampusRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHODepartmentCampusRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHODepartmentRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHOMajorCampusRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHOMajorRequest;
import com.portalevent.core.adminho.synchronizedmanagement.model.request.AdminHOSemesterRequest;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.ResponseObjectAPI;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.repository.DepartmentApiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author thangncph26123
 */
@Component
@Slf4j
public class CallApiIdentity {

    private final PortalEventsSession portalEventsSession;

    private final DepartmentApiRepository departmentApiRepository;


    @Value("${domain.identity}")
    private String domainIdentity;

    @Value("${identity.clientId}")
    private String clientId;

    @Value("${identity.clientSecret}")
    private String clientSecret;

    public CallApiIdentity(PortalEventsSession portalEventsSession, DepartmentApiRepository departmentApiRepository) {
        this.portalEventsSession = portalEventsSession;
        this.departmentApiRepository = departmentApiRepository;
    }

    /**
     * @param listUserCode: Truyền một list id của các user cần lấy thông tìn
     * @return Trả về 1 list SimpleResponse (là các user có id được truyền vào bằng list)
     */
    public List<SimpleResponse> handleCallApiGetListUserByListId(List<String> listUserCode) {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-detail-users";
            WebClient webClient = WebClient.create(apiUrl);

            Map<String, Object> requestBody = getCurrentClientAuthorizeProps();
            requestBody.put("userCodes", listUserCode);

            Mono<ResponseObjectAPI<List<SimpleResponse>>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<List<SimpleResponse>> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }
        } catch (Exception e) {
            log.error("Error when calling api get list user by list id: {}", e.getMessage());
            throw new RestApiException(Message.INVALID_USER);
        }
    }

//    /**
//     * @param listUserCode: Truyền một list id của các user cần lấy thông tìn
//     * @return Trả về 1 list SimpleResponse (là các user có id được truyền vào bằng list)
//     */
//    public List<SimpleResponse> handleCallApiGetListUserByListIdTest(List<String> listUserCode) {
//        try {
//            log.info("lstUserIdToGet + {}", listUserCode);
//            String apiUrl = domainIdentity + "/api/connector/get-detail-users";
//            WebClient webClient = WebClient.create(apiUrl);
//
//            Map<String, Object> requestBody =  getCurrentClientAuthorizeProps();
//            requestBody.put("userCodes", listUserCode);
//            requestBody.put("campusCode", portalEventsSession.getCurrentTrainingFacilityCode());
//            requestBody.put("subjectCode", portalEventsSession.getCurrentSubjectCode());
//            log.info("Request: {}", requestBody);
//
//            Mono<List<SimpleResponse>> responseMono = webClient
//                    .post()
//                    .body(BodyInserters.fromValue(requestBody))
//                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<List<SimpleResponse>>() {});
//
//            return responseMono.block();
//        } catch (Exception e) {
//            log.error("Error when calling api get list user by list id: {}", e.getMessage());
//            throw new RestApiException(Message.INVALID_USER);
//        }
//    }

    /**
     * @param roleCode: Truyền vào Constants role
     * @return List tất cả các user có role được truyền vào
     */
    public List<SimpleResponse> handleCallApiGetUserByRoleAndModule(String roleCode, String eventId) {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-list-user-by-role-code";
            WebClient webClient = WebClient.create(apiUrl);

            List<String> departmentCodes = departmentApiRepository.getDepartmentCodeByEventId(eventId);

            Map<String, Object> requestBody = getCurrentClientAndCampusSubjectAuthorizeProps();
            requestBody.put("roleCode", roleCode);
            requestBody.put("listDepartmentCode", departmentCodes);
            Mono<ResponseObjectAPI<List<SimpleResponse>>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<List<SimpleResponse>> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    /**
     * @param roleCode: Truyền vào Constants role và cơ sở
     * @return List tất cả các user có role được truyền vào
     */
    public List<SimpleResponse> handleCallApiGetUserByRoleAndModuleAndCampus(String roleCode) {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-list-user-by-role-code-and-campus-code";
            WebClient webClient = WebClient.create(apiUrl);

            Map<String, Object> requestBody = getCurrentClientAndCampusSubjectAuthorizeProps();
            requestBody.put("roleCode", roleCode);
            requestBody.put("campusCode", portalEventsSession.getCurrentTrainingFacilityCode());
            Mono<ResponseObjectAPI<List<SimpleResponse>>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<List<SimpleResponse>> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    /**
     * @param userCode vào id của 1 user cần lấy thông tin
     * @return Trả về thông tin của 1 user
     */
    public SimpleResponse handleCallApiGetUserById(String userCode) {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-user-by-id";
            WebClient webClient = WebClient.create(apiUrl);

            Map<String, Object> requestBody = getCurrentClientAuthorizeProps();
            requestBody.put("userCode", userCode);

            Mono<ResponseObjectAPI<SimpleResponse>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<SimpleResponse> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new RestApiException(Message.INVALID_USER);
        }
    }

//    /**
//     * lấy data subject từ identity
//     *
//     * @return
//     */
//    public List<DepartmentResponse> handleCallApiGetDepartments() {
//        try {
//            String apiUrl = domainIdentity + "/api/connector/get-departments";
//
//            WebClient webClient = WebClient.create(apiUrl);
//
//            Map<String, Object> requestBody =  getCurrentClientAndCampusSubjectAuthorizeProps();
//
//            Mono<List<DepartmentResponse>> responseMono = webClient
//                    .post()
//                    .body(BodyInserters.fromValue(requestBody))
//                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<>() {});
//
//            return responseMono.block();
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//            log.error("Error when call api get list user by list id: {}", e.getMessage());
//            throw new RestApiException(Message.INVALID_USER);
//        }
//    }

    /**
     * lấy data campus từ identity
     */
    public List<AdminHOCampusRequest> handleCallApiGetCampusByStatus() {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-campus-by-status";

            WebClient webClient = WebClient.create(apiUrl);

            Map<String, Object> requestBody = getCurrentClientAuthorizeProps();

            Mono<ResponseObjectAPI<List<AdminHOCampusRequest>>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<List<AdminHOCampusRequest>> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }

        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.error("Error when call api get list user by list id: {}", e.getMessage());
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    public List<AdminHODepartmentRequest> handleCallApiGetDepartmentsByStatus() {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-departments-by-status";

            WebClient webClient = WebClient.create(apiUrl);

            Map<String, Object> requestBody = getCurrentClientAuthorizeProps();

            Mono<ResponseObjectAPI<List<AdminHODepartmentRequest>>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<List<AdminHODepartmentRequest>> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.error("Error when call api get list user by list id: {}", e.getMessage());
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    public List<AdminHOMajorRequest> handleCallApiGetMajorsByStatus() {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-majors-by-status";

            WebClient webClient = WebClient.create(apiUrl);

            Map<String, Object> requestBody = getCurrentClientAuthorizeProps();

            Mono<ResponseObjectAPI<List<AdminHOMajorRequest>>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<List<AdminHOMajorRequest>> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.error("Error when call api get list user by list id: {}", e.getMessage());
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    public List<AdminHODepartmentCampusRequest> handleCallApiGetDepartmentCampusByStatus() {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-department-campus-by-status";

            WebClient webClient = WebClient.create(apiUrl);

            Map<String, Object> requestBody = getCurrentClientAuthorizeProps();

            Mono<ResponseObjectAPI<List<AdminHODepartmentCampusRequest>>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<List<AdminHODepartmentCampusRequest>> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.error("Error when call api get list user by list id: {}", e.getMessage());
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    public List<AdminHOMajorCampusRequest> handleCallApiGetMajorCampusByStatus() {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-major-campus-by-status";

            WebClient webClient = WebClient.create(apiUrl);

            Map<String, Object> requestBody = getCurrentClientAuthorizeProps();

            Mono<ResponseObjectAPI<List<AdminHOMajorCampusRequest>>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<List<AdminHOMajorCampusRequest>> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.error("Error when call api get list user by list id: {}", e.getMessage());
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    public List<AdminHOSemesterRequest> handleCallApiGetSemestersByStatus() {
        try {
            String apiUrl = domainIdentity + "/api/connector/get-semesters-by-status";

            WebClient webClient = WebClient.create(apiUrl);

            Map<String, Object> requestBody = getCurrentClientAuthorizeProps();

            Mono<ResponseObjectAPI<List<AdminHOSemesterRequest>>> responseMono = webClient
                    .post()
                    .body(BodyInserters.fromValue(requestBody))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<>() {
                    });
            if (responseMono.block() != null) {
                ResponseObjectAPI<List<AdminHOSemesterRequest>> responseObject = responseMono.block();
                if (responseObject.isSuccess()) {
                    return responseObject.getData();
                } else {
                    throw new RestApiException(Message.INVALID_USER);
                }
            } else {
                throw new RestApiException(Message.INVALID_USER);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            log.error("Error when call api get list user by list id: {}", e.getMessage());
            throw new RestApiException(Message.INVALID_USER);
        }
    }

    private Map<String, Object> getCurrentClientAndCampusSubjectAuthorizeProps() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("clientId", clientId);
        requestBody.put("clientSecret", clientSecret);
        requestBody.put("campusCode", portalEventsSession.getCurrentTrainingFacilityCode());
        requestBody.put("subjectCode", portalEventsSession.getCurrentSubjectCode());
        return requestBody;
    }

    private Map<String, Object> getCurrentClientAuthorizeProps() {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("clientId", clientId);
        requestBody.put("clientSecret", clientSecret);
        return requestBody;
    }

}
