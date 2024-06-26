package com.portalevent.core.adminho.objectmanagement.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * Class này để chứa dữ liệu bên FE để search và hiển thị list object
 */
@Getter
@Setter
public class AdminHOObjectManagementListRequest extends PageableRequest {

    private String name;

}
