package com.portalevent.core.adminh.eventapprovedmanagement.service;

import com.portalevent.core.adminh.eventapprovedmanagement.model.request.AdminHeaEventApprovedRequest;
import com.portalevent.core.adminh.eventapprovedmanagement.model.response.AdminHeaEventApprovedResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminHeaEventApprovedService {
    PageableObject<AdminHeaEventApprovedResponse> getListEventAppoved(AdminHeaEventApprovedRequest req);

    List<AewaEventCategoryResponse> getEventCategory();

}
