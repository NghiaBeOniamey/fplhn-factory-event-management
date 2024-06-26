package com.portalevent.core.adminh.objectmanagement.service;

import com.portalevent.core.adminh.objectmanagement.model.request.AdminHObjectManagementCreatRequest;
import com.portalevent.core.adminh.objectmanagement.model.request.AdminHObjectManagementListRequest;
import com.portalevent.core.adminh.objectmanagement.model.response.AdminHObjectManagementListResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Object;
import jakarta.validation.Valid;

public interface AdminHObjectManagementService {

    //Hiển thị list object và tìm kiếm
    PageableObject<AdminHObjectManagementListResponse> getListObject(AdminHObjectManagementListRequest request);

    //Hiển thị 1 object trong list object
    ResponseObject getDetailObject(String id);

    //Add object vào list object
    Object postObject(@Valid AdminHObjectManagementCreatRequest request);

    //Update object
    Object updateObject(String id, @Valid AdminHObjectManagementCreatRequest request);

    //Delete object
    Boolean deleteObject(String id);

}
