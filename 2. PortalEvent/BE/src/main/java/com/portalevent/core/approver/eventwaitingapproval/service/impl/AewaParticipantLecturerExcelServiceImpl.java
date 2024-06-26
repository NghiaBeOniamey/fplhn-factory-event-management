package com.portalevent.core.approver.eventwaitingapproval.service.impl;

import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaParticipantLecturerExcelResponse;
import com.portalevent.core.approver.eventwaitingapproval.repository.AewaParticipantLecturerRepository;
import com.portalevent.core.approver.eventwaitingapproval.service.AewaParticipantLecturerExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AewaParticipantLecturerExcelServiceImpl implements AewaParticipantLecturerExcelService {

    private final AewaParticipantLecturerRepository aewaParticipantLecturerRepository;

    @Override
    public List<AewaParticipantLecturerExcelResponse> findAllParticipantLecturer(String subjectCode, String trainingFacilityCode, String semesterName) {
        return aewaParticipantLecturerRepository.findAllParticipantLecturerWithSubjectCode(subjectCode, trainingFacilityCode, semesterName);
    }
}
