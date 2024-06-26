package com.portalevent.core.admin.eventapprovedmanagement.service.impl;

import com.portalevent.core.admin.eventapprovedmanagement.model.request.AdminerEventApprovedRequest;
import com.portalevent.core.admin.eventapprovedmanagement.model.response.AdminerEventApprovedResponse;
import com.portalevent.core.admin.eventapprovedmanagement.repository.AdminerEPEventRepository;
import com.portalevent.core.admin.eventapprovedmanagement.service.AdminerEventApprovedService;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventCategoryResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminerEventApprovedServiceImpl implements AdminerEventApprovedService {

    private final AdminerEPEventRepository eventRepository;

    private final PortalEventsSession portalEventsSession;

    public AdminerEventApprovedServiceImpl(AdminerEPEventRepository eventRepository, PortalEventsSession portalEventsSession) {
        this.eventRepository = eventRepository;
        this.portalEventsSession = portalEventsSession;
    }

    /**
     * @param req: Thông tin sự kiện cần tìm kiếm
     * @return Danh sách các sự kiện thỏa mãn điều kiện
     */
    @Override
    public PageableObject<AdminerEventApprovedResponse> getListEventAppoved(AdminerEventApprovedRequest req) {
        Pageable pageable = PageRequest.of(req.getPage(), req.getSize());
        Page<AdminerEventApprovedResponse> res = eventRepository.getListEventAppoved(pageable, req, portalEventsSession.getData());
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
