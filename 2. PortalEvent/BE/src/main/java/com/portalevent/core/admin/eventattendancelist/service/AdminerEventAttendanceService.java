package com.portalevent.core.admin.eventattendancelist.service;

import com.portalevent.core.admin.eventattendancelist.model.request.AdminerEventAttendanceSearchRequest;
import com.portalevent.core.admin.eventattendancelist.model.response.AdminerAttendanceResponse;
import com.portalevent.core.admin.eventattendancelist.model.response.AdminerEventAttendanceListResponse;
import com.portalevent.core.approver.eventattendancelist.model.response.ArlAttendanceResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminerEventAttendanceService {
    PageableObject<AdminerEventAttendanceListResponse> getAllAttendance(AdminerEventAttendanceSearchRequest request);

    List<AdminerAttendanceResponse> getAttendanceList(String idEvent);

}
