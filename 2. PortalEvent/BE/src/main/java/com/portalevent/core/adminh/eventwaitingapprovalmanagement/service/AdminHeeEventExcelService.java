package com.portalevent.core.adminh.eventwaitingapprovalmanagement.service;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHSDRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHeeEventExcelResponse;

import java.util.List;

public interface AdminHeeEventExcelService {

    List<AdminHeeEventExcelResponse> findAllEventExcel(AdminHSDRequest request);

}
