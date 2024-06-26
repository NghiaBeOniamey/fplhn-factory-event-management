package com.portalevent.core.admin.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.respone.AdminerParticipantLecturerResponse;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.repository.AdminerParticipantLecturerRepository;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.service.AdminerParticipantLecturerExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminerParticipantLecturerExcelServiceImpl implements AdminerParticipantLecturerExcelService {

    private final AdminerParticipantLecturerRepository adpParticipantLecturerRepository;

    @Override
    public List<AdminerParticipantLecturerResponse> findAllParticipantLecturer(
            String subjectCode, String trainingFacilityCode, String semesterName) {
        return adpParticipantLecturerRepository
                .findAllParticipantLecturerWithSubjectCode(subjectCode, trainingFacilityCode, semesterName);
    }

}
