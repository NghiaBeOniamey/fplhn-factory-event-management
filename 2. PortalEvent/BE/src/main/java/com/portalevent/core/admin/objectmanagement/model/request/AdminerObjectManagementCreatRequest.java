package com.portalevent.core.admin.objectmanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Class này để chứa dữ liệu bên FE để add object
 */

@Getter
@Setter
public class AdminerObjectManagementCreatRequest {

    @NotBlank(message = "Không để trống tên thể loại!!!")
    private String name;

}
