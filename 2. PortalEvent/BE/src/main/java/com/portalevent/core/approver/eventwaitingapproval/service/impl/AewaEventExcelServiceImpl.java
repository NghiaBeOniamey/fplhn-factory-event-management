package com.portalevent.core.approver.eventwaitingapproval.service.impl;

import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventExcelResponse;
import com.portalevent.core.approver.eventwaitingapproval.repository.AewaEventExcelRepository;
import com.portalevent.core.approver.eventwaitingapproval.service.AewaEventExcelService;
import com.portalevent.infrastructure.session.PortalEventsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AewaEventExcelServiceImpl implements AewaEventExcelService {

    private final AewaEventExcelRepository aewaEventExcelRepository;

    private final PortalEventsSession portalEventsSession;

    @Override
    public List<AewaEventExcelResponse> findAllEventExcel(String idSemester) {
        return aewaEventExcelRepository
                .findAllEventExcel(idSemester, portalEventsSession.getData());
    }
}
