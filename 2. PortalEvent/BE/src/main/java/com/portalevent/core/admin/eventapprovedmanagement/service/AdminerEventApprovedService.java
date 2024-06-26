package com.portalevent.core.admin.eventapprovedmanagement.service;

import com.portalevent.core.admin.eventapprovedmanagement.model.request.AdminerEventApprovedRequest;
import com.portalevent.core.admin.eventapprovedmanagement.model.response.AdminerEventApprovedResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminerEventApprovedService {

    PageableObject<AdminerEventApprovedResponse> getListEventAppoved(AdminerEventApprovedRequest req);

    List<AewaEventCategoryResponse> getEventCategory();

}
