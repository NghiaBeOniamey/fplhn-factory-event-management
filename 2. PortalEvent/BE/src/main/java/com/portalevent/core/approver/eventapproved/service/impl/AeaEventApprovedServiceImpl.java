package com.portalevent.core.approver.eventapproved.service.impl;

import com.portalevent.core.approver.eventapproved.model.request.AeaEventApprovedRequest;
import com.portalevent.core.approver.eventapproved.model.response.AeaEventApprovedResponse;
import com.portalevent.core.approver.eventapproved.repository.AeaEventRepository;
import com.portalevent.core.approver.eventapproved.service.AeaEventApprovedService;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.PageableObject;
import java.util.List;

import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AeaEventApprovedServiceImpl implements AeaEventApprovedService {
    @Autowired
    private AeaEventRepository eventRepository;

    @Autowired
    private PortalEventsSession portalEventsSession;

    /**
     * @param req: Thông tin sự kiện cần tìm kiếm
     * @return Danh sách các sự kiện thỏa mãn điều kiện
     */
    @Override
    public PageableObject<AeaEventApprovedResponse> getListEventAppoved(AeaEventApprovedRequest req) {
        req.setName(req.getName().replaceAll("\\s+", " "));
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<AeaEventApprovedResponse> res = eventRepository.getListEventAppoved(pageable, req, portalEventsSession.getData());
        return new PageableObject(res);
    }

    /**
     * @return Danh sách thể loại sự kiện
     */
    @Override
    public List<AewaEventCategoryResponse> getEventCategory() {
        return eventRepository.getListEventCategory();
    }
}
