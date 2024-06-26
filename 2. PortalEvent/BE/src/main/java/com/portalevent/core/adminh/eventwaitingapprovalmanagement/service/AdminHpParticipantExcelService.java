package com.portalevent.core.adminh.eventwaitingapprovalmanagement.service;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHpParticipantResponse;

import java.util.List;

public interface AdminHpParticipantExcelService {

    List<AdminHpParticipantResponse> findAllParticipant(String subjectCode);

}
