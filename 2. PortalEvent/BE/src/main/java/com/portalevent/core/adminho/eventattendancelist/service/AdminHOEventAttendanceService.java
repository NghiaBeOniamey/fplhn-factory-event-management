package com.portalevent.core.adminho.eventattendancelist.service;

import com.portalevent.core.adminho.eventattendancelist.model.request.AdminHOEventAttendanceSearchRequest;
import com.portalevent.core.adminho.eventattendancelist.model.response.AdminHOAttendanceResponse;
import com.portalevent.core.adminho.eventattendancelist.model.response.AdminHOEventAttendanceListResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminHOEventAttendanceService {
    PageableObject<AdminHOEventAttendanceListResponse> getAllAttendance(AdminHOEventAttendanceSearchRequest request);

    List<AdminHOAttendanceResponse> getAttendanceList(String idEvent);
}
