package com.portalevent.core.adminho.eventwaitingapprovalmanagement.service;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOewCommentEventDetailRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOewEventListRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOewCommentEventDetailResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOewEventListResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;

public interface AdminHOewEventService {

    PageableObject<AdminHOewEventListResponse> getListEventNotApproved(AdminHOewEventListRequest request);

    ResponseObject getDetailEventApproved(String id);

    ResponseObject getEventCategory();

    ResponseObject getEventMajor();

    PageableObject<AdminHOewCommentEventDetailResponse> getCommentEventById(AdminHOewCommentEventDetailRequest request);

}
