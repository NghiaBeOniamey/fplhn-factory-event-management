package com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHSDRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHeeEventExcelResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.repository.AdminHeeEventExcelRepository;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.AdminHeeEventExcelService;
import com.portalevent.infrastructure.session.PortalEventsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminHeeEventExcelServiceImpl implements AdminHeeEventExcelService {


    private final AdminHeeEventExcelRepository adeeEventExcelRepository;

    private final PortalEventsSession portalEventsSession;

    @Override
    public List<AdminHeeEventExcelResponse> findAllEventExcel(AdminHSDRequest request) {
        return adeeEventExcelRepository.findAllEventExcel(request, portalEventsSession.getData());
    }

}
