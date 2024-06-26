package com.portalevent.core.approver.eventclosed.service;

import com.portalevent.core.approver.eventclosed.model.request.AecEventCloseRequest;
import com.portalevent.core.approver.eventclosed.model.response.AecEventCloseResponse;
import com.portalevent.core.approver.eventclosed.model.response.AecPropsResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AecEventClosedService {
    PageableObject<AecEventCloseResponse> getAllEventClose(AecEventCloseRequest request);

    List<AecPropsResponse> getAllMajor();

    List<AecPropsResponse> getAllObject();

    List<AecPropsResponse> getAllCategory();

    List<AecPropsResponse> getAllSemester();
}
