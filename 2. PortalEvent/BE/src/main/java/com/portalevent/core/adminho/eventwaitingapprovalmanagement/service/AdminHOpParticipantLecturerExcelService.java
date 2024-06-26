package com.portalevent.core.adminho.eventwaitingapprovalmanagement.service;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOpParticipantLecturerResponse;

import java.util.List;

public interface AdminHOpParticipantLecturerExcelService {

    List<AdminHOpParticipantLecturerResponse> findAllParticipantLecturer(String subjectCode);

}
