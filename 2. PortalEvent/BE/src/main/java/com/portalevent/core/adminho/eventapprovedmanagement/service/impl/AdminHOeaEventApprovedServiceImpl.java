package com.portalevent.core.adminho.eventapprovedmanagement.service.impl;

import com.portalevent.core.adminho.eventapprovedmanagement.model.request.AdminHOeaEventApprovedRequest;
import com.portalevent.core.adminho.eventapprovedmanagement.model.response.AdminHOeaEventApprovedResponse;
import com.portalevent.core.adminho.eventapprovedmanagement.repository.AdminHOeaEventRepository;
import com.portalevent.core.adminho.eventapprovedmanagement.service.AdminHOeaEventApprovedService;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHOeaEventApprovedServiceImpl implements AdminHOeaEventApprovedService {

    private final AdminHOeaEventRepository eventRepository;

    private final PortalEventsSession portalEventsSession;

    public AdminHOeaEventApprovedServiceImpl(AdminHOeaEventRepository eventRepository, PortalEventsSession portalEventsSession) {
        this.eventRepository = eventRepository;
        this.portalEventsSession = portalEventsSession;
    }

    /**
     * @param req: Thông tin sự kiện cần tìm kiếm
     * @return Danh sách các sự kiện thỏa mãn điều kiện
     */
    @Override
    public PageableObject<AdminHOeaEventApprovedResponse> getListEventAppoved(AdminHOeaEventApprovedRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<AdminHOeaEventApprovedResponse> res = eventRepository.getListEventAppoved(pageable, req, portalEventsSession.getData());
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
