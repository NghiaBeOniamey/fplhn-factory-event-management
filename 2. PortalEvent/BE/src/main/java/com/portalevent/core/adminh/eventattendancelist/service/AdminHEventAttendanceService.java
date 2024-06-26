package com.portalevent.core.adminh.eventattendancelist.service;

import com.portalevent.core.adminh.eventattendancelist.model.request.AdminHEventAttendanceSearchRequest;
import com.portalevent.core.adminh.eventattendancelist.model.response.AdminHAttendanceResponse;
import com.portalevent.core.adminh.eventattendancelist.model.response.AdminHEventAttendanceListResponse;
import com.portalevent.core.approver.eventattendancelist.model.response.ArlAttendanceResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminHEventAttendanceService {
    PageableObject<AdminHEventAttendanceListResponse> getAllAttendance(AdminHEventAttendanceSearchRequest request);

    List<AdminHAttendanceResponse> getAttendanceList(String idEvent);
}
