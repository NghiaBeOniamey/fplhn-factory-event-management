package com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOpParticipantResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.repository.AdminHOpParticipantRepository;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.AdminHOpParticipantExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminHOpParticipantExcelServiceImpl implements AdminHOpParticipantExcelService {

    private final AdminHOpParticipantRepository adParticipantRepository;

    @Override
    public List<AdminHOpParticipantResponse> findAllParticipant(String subjectCode) {
        return adParticipantRepository
                .findAllParticipantWithSubjectCode(subjectCode);
    }

}
