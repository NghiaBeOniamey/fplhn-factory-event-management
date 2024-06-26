package com.portalevent.core.approver.eventattendancelist.service;

import com.portalevent.core.approver.eventattendancelist.model.request.AealEventAttendanceSearchRequest;
import com.portalevent.core.approver.eventattendancelist.model.response.AealEventAttendanceListResponse;
import com.portalevent.core.approver.eventattendancelist.model.response.ArlAttendanceResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AealEventAttendanceService {
    List<AealEventAttendanceListResponse> getAttendanceListByIdEvent(String idEvent);

    PageableObject<AealEventAttendanceListResponse> getAllAttendance(AealEventAttendanceSearchRequest request);

    List<ArlAttendanceResponse> getAttendanceList(String idEvent);
}
