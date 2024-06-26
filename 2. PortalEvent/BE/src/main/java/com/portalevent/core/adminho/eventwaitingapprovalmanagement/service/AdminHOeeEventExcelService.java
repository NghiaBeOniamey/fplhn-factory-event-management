package com.portalevent.core.adminho.eventwaitingapprovalmanagement.service;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOSCDRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOeeEventExcelResponse;

import java.util.List;

public interface AdminHOeeEventExcelService {

    List<AdminHOeeEventExcelResponse> findAllEventExcel(AdminHOSCDRequest request);

}
