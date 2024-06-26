package com.portalevent.core.admin.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerSDRequest;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerEventExcelResponse;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.repository.AdminerEventExcelRepository;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.service.AdminerEventExcelService;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHSDRequest;
import com.portalevent.infrastructure.session.PortalEventsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminerEventExcelServiceImpl implements AdminerEventExcelService {

    private final AdminerEventExcelRepository adeeEventExcelRepository;

    private final PortalEventsSession portalEventsSession;

    @Override
    public List<AdminerEventExcelResponse>
    findAllEventExcel(AdminerSDRequest request) {
        return adeeEventExcelRepository.findAllEventExcel(request, portalEventsSession.getData());
    }

}
