package com.portalevent.core.approver.eventwaitingapproval.service.impl;

import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaParticipantExcelResponse;
import com.portalevent.core.approver.eventwaitingapproval.repository.AewaParticipantRepository;
import com.portalevent.core.approver.eventwaitingapproval.service.AewaParticipantExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AewaParticipantExcelServiceImpl implements AewaParticipantExcelService {

    private final AewaParticipantRepository aewaParticipantRepository;

    @Override
    public List<AewaParticipantExcelResponse> findAllParticipant(String subjectCode,
                                                                 String trainingFacilityCode,
                                                                 String semesterName) {
        return aewaParticipantRepository.findAllParticipantWithSubjectCode(subjectCode,
                trainingFacilityCode, semesterName);
    }
}
