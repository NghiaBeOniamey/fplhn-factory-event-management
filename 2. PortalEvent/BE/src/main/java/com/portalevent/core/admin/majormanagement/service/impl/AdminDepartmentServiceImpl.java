package com.portalevent.core.admin.majormanagement.service.impl;

import com.portalevent.core.admin.majormanagement.model.response.AdminDepartmentResponse;
import com.portalevent.core.admin.majormanagement.repository.AdminDepartmentRepository;
import com.portalevent.core.admin.majormanagement.service.AdminDepartmentService;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.infrastructure.session.PortalEventsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminDepartmentServiceImpl implements AdminDepartmentService {

    private final AdminDepartmentRepository adminDepartmentRepository;

    private final PortalEventsSession session;

    @Override
    public List<AdminDepartmentResponse> getDepartmentList(String value) {
        return adminDepartmentRepository.getDepartmentList(value, session.getData());
    }

}
