package com.portalevent.core.adminho.majormanagement.model.request;

import com.portalevent.infrastructure.constant.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminHODepartmentRequest {

    @NotEmpty(message = "Mã không được để trống!")
    private String code;

    @NotEmpty(message = "Tên không được để trống!")
    private String name;

    @NotEmpty(message = "Email không được để trống!")
    @Email(message = "Email không đúng định dạng!", regexp = Constants.REGEX_EMAIL_FPT)
    private String email;

    private String mainMajor;

}
