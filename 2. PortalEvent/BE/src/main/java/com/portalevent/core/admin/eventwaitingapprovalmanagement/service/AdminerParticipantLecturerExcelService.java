package com.portalevent.core.admin.eventwaitingapprovalmanagement.service;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerParticipantLecturerResponse;

import java.util.List;

public interface AdminerParticipantLecturerExcelService {

    List<AdminerParticipantLecturerResponse> findAllParticipantLecturer(
            String subjectCode, String trainingFacilityCode, String semesterName);

}
