package fplhn.udpm.identity.core.connection.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetListUserByRoleCodeAndCampusCodeRequest extends ClientRequest {

    @NotNull
    private String roleCode;

    @NotNull
    private String campusCode;

}
