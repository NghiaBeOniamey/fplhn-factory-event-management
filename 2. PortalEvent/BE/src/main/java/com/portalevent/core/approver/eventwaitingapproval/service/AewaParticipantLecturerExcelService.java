package com.portalevent.core.approver.eventwaitingapproval.service;

import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaParticipantLecturerExcelResponse;

import java.util.List;

public interface AewaParticipantLecturerExcelService {

    List<AewaParticipantLecturerExcelResponse> findAllParticipantLecturer(String subjectCode,
                                                                          String trainingFacilityCode,
                                                                          String semesterName);

}
