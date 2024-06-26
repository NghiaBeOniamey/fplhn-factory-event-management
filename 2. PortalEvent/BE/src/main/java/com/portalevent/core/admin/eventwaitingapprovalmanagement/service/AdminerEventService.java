package com.portalevent.core.admin.eventwaitingapprovalmanagement.service;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerCommentEventDetailRequest;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerEventListRequest;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerCommentEventDetailResponse;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerEventListResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;

public interface AdminerEventService {

    PageableObject<AdminerEventListResponse> getListEventNotApproved(AdminerEventListRequest request);

    ResponseObject getDetailEventApproved(String id);

    ResponseObject getEventCategory();

    ResponseObject getEventMajor();

    ResponseObject getEventDepartment();

    PageableObject<AdminerCommentEventDetailResponse> getCommentEventById(AdminerCommentEventDetailRequest request);

}
