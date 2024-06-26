package fplhn.udpm.identity.infrastructure.security.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ModuleAvailableResponse {

    private String moduleCode;

    private String moduleName;

}
