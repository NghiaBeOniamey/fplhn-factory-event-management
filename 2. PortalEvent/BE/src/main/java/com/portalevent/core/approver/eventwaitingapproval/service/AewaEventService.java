package com.portalevent.core.approver.eventwaitingapproval.service;

import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaCommentEventDetailRequest;
import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaEventListRequest;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaCommentEventDetailResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventListResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;

public interface AewaEventService {
    PageableObject<AewaEventListResponse> getListEventNotApproved(AewaEventListRequest request);

    ResponseObject getDetailEventApproved(String id);

    ResponseObject getEventCategory();

    ResponseObject getEventMajor();

    PageableObject<AewaCommentEventDetailResponse> getCommentEventById(AewaCommentEventDetailRequest request);
}
