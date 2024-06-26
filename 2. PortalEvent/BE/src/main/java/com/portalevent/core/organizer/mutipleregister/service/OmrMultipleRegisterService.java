package com.portalevent.core.organizer.mutipleregister.service;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.mutipleregister.model.request.OmrFastRegisterRequest;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrEventDetailResponse;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrEventScheduleResponse;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrInfomationResponse;

import java.util.List;

/**
 * @author SonPT
 */
public interface OmrMultipleRegisterService {

    List<OmrEventScheduleResponse> getAllForCalendar();

    OmrInfomationResponse getAllInfo();

    List<OmrEventScheduleResponse> multipleRegister(List<OmrFastRegisterRequest> newEvents);

    OmrEventDetailResponse getDetail(String id);

}
