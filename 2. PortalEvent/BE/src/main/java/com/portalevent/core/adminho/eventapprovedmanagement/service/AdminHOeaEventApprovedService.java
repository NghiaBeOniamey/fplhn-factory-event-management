package com.portalevent.core.adminho.eventapprovedmanagement.service;

import com.portalevent.core.adminho.eventapprovedmanagement.model.request.AdminHOeaEventApprovedRequest;
import com.portalevent.core.adminho.eventapprovedmanagement.model.response.AdminHOeaEventApprovedResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.PageableObject;

import java.util.List;

public interface AdminHOeaEventApprovedService {

    PageableObject<AdminHOeaEventApprovedResponse> getListEventAppoved(AdminHOeaEventApprovedRequest req);

    List<AewaEventCategoryResponse> getEventCategory();

}
