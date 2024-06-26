package com.portalevent.core.adminh.categorymanagement.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Class này để hứng data từ input để thêm thể loại
 */

@Getter
@Setter
public class AdminHCategoryManagementCreateRequest {

    @NotBlank(message = "Không để trống tên thể loại!!!")
    private String name;

}
