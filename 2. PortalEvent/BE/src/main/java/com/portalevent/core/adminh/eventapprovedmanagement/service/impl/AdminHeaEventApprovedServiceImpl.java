package com.portalevent.core.adminh.eventapprovedmanagement.service.impl;

import com.portalevent.core.adminh.eventapprovedmanagement.model.request.AdminHeaEventApprovedRequest;
import com.portalevent.core.adminh.eventapprovedmanagement.model.response.AdminHeaEventApprovedResponse;
import com.portalevent.core.adminh.eventapprovedmanagement.repository.AdminHeaEventRepository;
import com.portalevent.core.adminh.eventapprovedmanagement.service.AdminHeaEventApprovedService;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHeaEventApprovedServiceImpl implements AdminHeaEventApprovedService {

    private final AdminHeaEventRepository eventRepository;

    private final PortalEventsSession portalEventsSession;

    public AdminHeaEventApprovedServiceImpl(AdminHeaEventRepository eventRepository, PortalEventsSession portalEventsSession) {
        this.eventRepository = eventRepository;
        this.portalEventsSession = portalEventsSession;
    }

    /**
     * @param req: Thông tin sự kiện cần tìm kiếm
     * @return Danh sách các sự kiện thỏa mãn điều kiện
     */
    @Override
    public PageableObject<AdminHeaEventApprovedResponse> getListEventAppoved(AdminHeaEventApprovedRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<AdminHeaEventApprovedResponse> res = eventRepository.getListEventAppoved(pageable, req, portalEventsSession.getData());
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
