package com.portalevent.core.adminho.eventwaitingapprovalmanagement.service;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOpParticipantResponse;

import java.util.List;

public interface AdminHOpParticipantExcelService {

    List<AdminHOpParticipantResponse> findAllParticipant(String subjectCode);

}
