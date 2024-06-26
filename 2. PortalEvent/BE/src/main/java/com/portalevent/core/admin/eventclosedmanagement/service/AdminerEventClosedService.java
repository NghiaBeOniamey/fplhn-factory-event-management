package com.portalevent.core.admin.eventclosedmanagement.service;

import com.portalevent.core.admin.eventclosedmanagement.model.request.AdminerEventCloseRequest;
import com.portalevent.core.admin.eventclosedmanagement.model.response.AdminerEventCloseResponse;
import com.portalevent.core.admin.eventclosedmanagement.model.response.AdminerPropsResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminerEventClosedService {

    PageableObject<AdminerEventCloseResponse> getAllEventClose(AdminerEventCloseRequest request);

    List<AdminerPropsResponse> getAllMajor();

    List<AdminerPropsResponse> getAllObject();

    List<AdminerPropsResponse> getAllCategory();

    List<AdminerPropsResponse> getAllSemester();

}
