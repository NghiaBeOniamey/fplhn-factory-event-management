package com.portalevent.core.approver.eventapproved.service;

import com.portalevent.core.approver.eventapproved.model.request.AeaEventApprovedRequest;
import com.portalevent.core.approver.eventapproved.model.response.AeaEventApprovedResponse;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.PageableObject;
import java.util.List;

public interface AeaEventApprovedService {
    PageableObject<AeaEventApprovedResponse> getListEventAppoved(AeaEventApprovedRequest req);

    List<AewaEventCategoryResponse> getEventCategory();
}
