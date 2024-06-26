package com.portalevent.core.approver.eventwaitingapproval.service;

import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaParticipantExcelResponse;

import java.util.List;

public interface AewaParticipantExcelService {

    List<AewaParticipantExcelResponse> findAllParticipant(String subjectCode,
                                                          String trainingFacilityCode,
                                                          String semesterName);

}
