package fplhn.udpm.identity.core.feature.campus.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifyCampusRequest {

    @NotNull(message = "Tên cơ sở không được để trống")
    @NotBlank(message = "Tên cơ sở không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,255}$", message = "Tên cơ sở không được vượt quá 255 ký tự")
    private String campusName;

    @NotNull(message = "Mã cơ sở không được để trống")
    @NotBlank(message = "Mã cơ sở không được để trống")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,255}$", message = "Mã cơ sở không được vượt quá 255 ký tự")
    private String campusCode;

}
