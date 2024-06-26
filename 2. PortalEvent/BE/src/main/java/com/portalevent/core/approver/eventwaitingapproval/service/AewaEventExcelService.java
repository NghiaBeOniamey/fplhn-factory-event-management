package com.portalevent.core.approver.eventwaitingapproval.service;

import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventExcelResponse;

import java.util.List;

public interface AewaEventExcelService {

    List<AewaEventExcelResponse> findAllEventExcel(String idSemester);

}
