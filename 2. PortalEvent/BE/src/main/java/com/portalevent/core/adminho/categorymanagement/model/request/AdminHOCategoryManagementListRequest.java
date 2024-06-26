package com.portalevent.core.adminho.categorymanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * class này để hứng data từ input tìm kiếm
 */
@Getter
@Setter
public class AdminHOCategoryManagementListRequest extends PageableRequest {

    private String name;

}
