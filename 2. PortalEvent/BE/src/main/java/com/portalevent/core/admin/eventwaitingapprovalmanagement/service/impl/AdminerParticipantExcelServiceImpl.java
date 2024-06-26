package com.portalevent.core.admin.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerParticipantResponse;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.repository.AdminerParticipantEWARepository;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.service.AdminerParticipantExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminerParticipantExcelServiceImpl implements AdminerParticipantExcelService {

    private final AdminerParticipantEWARepository adParticipantRepository;

    @Override
    public List<AdminerParticipantResponse> findAllParticipant(
            String subjectCode,
            String trainingFacilityCode,
            String semesterName) {
        return adParticipantRepository
                .findAllParticipantWithSubjectCode(subjectCode, trainingFacilityCode, semesterName);
    }

}
