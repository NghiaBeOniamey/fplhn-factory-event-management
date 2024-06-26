package com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOpParticipantLecturerResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.repository.AdminHOpParticipantLecturerRepository;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.AdminHOpParticipantLecturerExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminHOpParticipantLecturerExcelServiceImpl implements AdminHOpParticipantLecturerExcelService {

    private final AdminHOpParticipantLecturerRepository adpParticipantLecturerRepository;

    @Override
    public List<AdminHOpParticipantLecturerResponse> findAllParticipantLecturer(String subjectCode) {
        return adpParticipantLecturerRepository
                .findAllParticipantLecturerWithSubjectCode(subjectCode);
    }

}
