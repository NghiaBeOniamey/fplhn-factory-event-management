package fplhn.udpm.identity.core.authentication.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SwitchModuleRequest {

    private String moduleCode;

    private Long userId;

    private String userType;

    private String identityToken;

}
