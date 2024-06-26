package com.portalevent.core.adminh.eventwaitingapprovalmanagement.service;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHewCommentEventDetailRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHewEventListRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHewCommentEventDetailResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHewEventListResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;

public interface AdminHewEventService {

    PageableObject<AdminHewEventListResponse> getListEventNotApproved(AdminHewEventListRequest request);

    ResponseObject getDetailEventApproved(String id);

    ResponseObject getEventCategory();

    ResponseObject getEventMajor();

    ResponseObject getEventDepartment();

    PageableObject<AdminHewCommentEventDetailResponse> getCommentEventById(AdminHewCommentEventDetailRequest request);

}
