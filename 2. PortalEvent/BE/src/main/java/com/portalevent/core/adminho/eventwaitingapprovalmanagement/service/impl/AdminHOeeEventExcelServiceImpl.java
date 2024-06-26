package com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOSCDRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOeeEventExcelResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.repository.AdminHOeeEventExcelRepository;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.AdminHOeeEventExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminHOeeEventExcelServiceImpl implements AdminHOeeEventExcelService {


    private final AdminHOeeEventExcelRepository adeeEventExcelRepository;

    @Override
    public List<AdminHOeeEventExcelResponse> findAllEventExcel(AdminHOSCDRequest request) {
        return adeeEventExcelRepository.findAllEventExcel(request);
    }

}
