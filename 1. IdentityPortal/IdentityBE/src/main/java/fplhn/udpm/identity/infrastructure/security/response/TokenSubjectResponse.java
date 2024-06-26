package fplhn.udpm.identity.infrastructure.security.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TokenSubjectResponse {

    private Long userId;

    private String userCode;

    private String fullName;

    private String pictureUrl;

    private String host;

    private List<String> rolesCode;

    private List<String> rolesName;

    private String email;

    private String trainingFacilityCode;

    private String subjectCode;

    private String userType;

    private Long trainingFacilityId;

    private String trainingFacilityName;

    private String subjectName;

    private List<ModuleAvailableResponse> moduleAvailableResponses;

}
