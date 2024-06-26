package com.portalevent.core.admin.eventwaitingapprovalmanagement.service;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerParticipantResponse;

import java.util.List;

public interface AdminerParticipantExcelService {

    List<AdminerParticipantResponse> findAllParticipant(
            String subjectCode,
            String trainingFacilityCode,
            String semesterName);

}
