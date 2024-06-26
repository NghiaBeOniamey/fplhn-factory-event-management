package fplhn.udpm.identity.core.feature.module.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateModuleRequest {

    @NotNull(message = "Mã mô-đun không được để trống")
    @NotBlank(message = "Mã mô-đun không được để trống")
    private String moduleCode;

    @NotNull(message = "Tên mô-đun không được để trống")
    @NotBlank(message = "Tên mô-đun không được để trống")
    private String moduleName;

    @NotNull(message = "Địa chỉ mô-đun không được để trống")
    @NotBlank(message = "Địa chỉ mô-đun không được để trống")
    private String moduleUrl;

    private String redirectRoute;

}
