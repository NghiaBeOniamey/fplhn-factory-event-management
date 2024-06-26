package com.portalevent.core.adminho.objectmanagement.service;

import com.portalevent.core.adminho.objectmanagement.model.request.AdminHOObjectManagementCreatRequest;
import com.portalevent.core.adminho.objectmanagement.model.request.AdminHOObjectManagementListRequest;
import com.portalevent.core.adminho.objectmanagement.model.response.AdminHOObjectManagementListResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Object;
import jakarta.validation.Valid;

public interface AdminHOObjectManagementService {

    //Hiển thị list object và tìm kiếm
    PageableObject<AdminHOObjectManagementListResponse> getListObject(AdminHOObjectManagementListRequest request);

    //Hiển thị 1 object trong list object
    ResponseObject getDetailObject(String id);

    //Add object vào list object
    Object postObject(@Valid AdminHOObjectManagementCreatRequest request);

    //Update object
    Object updateObject(String id, @Valid AdminHOObjectManagementCreatRequest request);

    //Delete object
    Boolean deleteObject(String id);

}
