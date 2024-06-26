package com.portalevent.core.adminho.eventclosedmanagement.service;

import com.portalevent.core.adminho.eventclosedmanagement.model.request.AdminHOecEventCloseRequest;
import com.portalevent.core.adminho.eventclosedmanagement.model.response.AdminHOecEventCloseResponse;
import com.portalevent.core.adminho.eventclosedmanagement.model.response.AdminHOecPropsResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminHOecEventClosedService {

    PageableObject<AdminHOecEventCloseResponse> getAllEventClose(AdminHOecEventCloseRequest request);

    List<AdminHOecPropsResponse> getAllMajor();

    List<AdminHOecPropsResponse> getAllObject();

    List<AdminHOecPropsResponse> getAllCategory();

    List<AdminHOecPropsResponse> getAllSemester();

}
