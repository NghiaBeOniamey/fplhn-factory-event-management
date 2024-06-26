package fplhn.udpm.identity.core.feature.major.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateMajorRequest {

    @NotNull(message = "Mã ngành không được để trống")
    private String majorCode;

    @NotNull(message = "Tên ngành không được để trống")
    private String majorName;

}
