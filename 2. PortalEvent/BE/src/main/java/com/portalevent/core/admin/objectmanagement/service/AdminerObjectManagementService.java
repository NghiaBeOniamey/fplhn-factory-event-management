package com.portalevent.core.admin.objectmanagement.service;

import com.portalevent.core.admin.objectmanagement.model.request.AdminerObjectManagementCreatRequest;
import com.portalevent.core.admin.objectmanagement.model.request.AdminerObjectManagementListRequest;
import com.portalevent.core.admin.objectmanagement.model.response.AdminerObjectManagementListResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Object;
import jakarta.validation.Valid;

public interface AdminerObjectManagementService {

    //Hiển thị list object và tìm kiếm
    PageableObject<AdminerObjectManagementListResponse> getListObject(AdminerObjectManagementListRequest request);

    //Hiển thị 1 object trong list object
    ResponseObject getDetailObject(String id);

    //Add object vào list object
    Object postObject(@Valid AdminerObjectManagementCreatRequest request);

    //Update object
    Object updateObject(String id, @Valid AdminerObjectManagementCreatRequest request);

    //Delete object
    Boolean deleteObject(String id);

}
