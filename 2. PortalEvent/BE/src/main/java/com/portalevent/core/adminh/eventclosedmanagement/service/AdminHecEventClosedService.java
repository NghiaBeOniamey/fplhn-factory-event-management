package com.portalevent.core.adminh.eventclosedmanagement.service;

import com.portalevent.core.adminh.eventclosedmanagement.model.request.AdminHecEventCloseRequest;
import com.portalevent.core.adminh.eventclosedmanagement.model.response.AdminHecEventCloseResponse;
import com.portalevent.core.adminh.eventclosedmanagement.model.response.AdminHecPropsResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminHecEventClosedService {

    PageableObject<AdminHecEventCloseResponse> getAllEventClose(AdminHecEventCloseRequest request);

    List<AdminHecPropsResponse> getAllMajor();

    List<AdminHecPropsResponse> getAllObject();

    List<AdminHecPropsResponse> getAllCategory();

    List<AdminHecPropsResponse> getAllSemester();

}
