package fplhn.udpm.identity.core.connection.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetListStudentRequest extends ClientRequest {

    @NotNull
    private List<String> studentCodes;

}
