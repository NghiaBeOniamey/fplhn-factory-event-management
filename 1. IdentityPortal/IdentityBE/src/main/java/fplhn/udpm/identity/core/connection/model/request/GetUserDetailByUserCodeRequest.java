package fplhn.udpm.identity.core.connection.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetUserDetailByUserCodeRequest extends ClientRequest {

    @NotNull
    private String userCode;

}
