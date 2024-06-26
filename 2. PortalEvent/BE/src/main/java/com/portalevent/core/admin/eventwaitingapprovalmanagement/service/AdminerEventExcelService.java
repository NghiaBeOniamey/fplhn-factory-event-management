package com.portalevent.core.admin.eventwaitingapprovalmanagement.service;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerSDRequest;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerEventExcelResponse;

import java.util.List;

public interface AdminerEventExcelService {

    List<AdminerEventExcelResponse>
    findAllEventExcel(AdminerSDRequest request);

}
