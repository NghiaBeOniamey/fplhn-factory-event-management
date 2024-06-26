package com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHpParticipantLecturerResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.repository.AdminHpParticipantLecturerRepository;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.AdminHpParticipantLecturerExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminHpParticipantLecturerExcelServiceImpl implements AdminHpParticipantLecturerExcelService {

    private final AdminHpParticipantLecturerRepository adpParticipantLecturerRepository;

    @Override
    public List<AdminHpParticipantLecturerResponse> findAllParticipantLecturer(String subjectCode) {
        return adpParticipantLecturerRepository
                .findAllParticipantLecturerWithSubjectCode(subjectCode);
    }

}
