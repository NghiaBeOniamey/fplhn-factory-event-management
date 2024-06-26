package com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHpParticipantResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.repository.AdminHpParticipantRepository;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.AdminHpParticipantExcelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminHpParticipantExcelServiceImpl implements AdminHpParticipantExcelService {

    private final AdminHpParticipantRepository adParticipantRepository;

    @Override
    public List<AdminHpParticipantResponse> findAllParticipant(String subjectCode) {
        return adParticipantRepository
                .findAllParticipantWithSubjectCode(subjectCode);
    }

}
