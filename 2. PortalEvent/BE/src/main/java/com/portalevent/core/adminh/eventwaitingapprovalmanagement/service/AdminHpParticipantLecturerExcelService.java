package com.portalevent.core.adminh.eventwaitingapprovalmanagement.service;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHpParticipantLecturerResponse;

import java.util.List;

public interface AdminHpParticipantLecturerExcelService {

    List<AdminHpParticipantLecturerResponse> findAllParticipantLecturer(String subjectCode);

}
