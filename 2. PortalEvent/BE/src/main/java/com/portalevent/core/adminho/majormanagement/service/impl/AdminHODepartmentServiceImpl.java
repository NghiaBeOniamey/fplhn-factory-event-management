package com.portalevent.core.adminho.majormanagement.service.impl;

import com.portalevent.core.adminho.majormanagement.model.request.AdminHOSearchDepartmentsRequest;
import com.portalevent.core.adminho.majormanagement.model.response.AdminHODepartmentResponse;
import com.portalevent.core.adminho.majormanagement.repository.AdminHODepartmentRepository;
import com.portalevent.core.adminho.majormanagement.service.AdminHODepartmentService;
import com.portalevent.util.CallApiIdentity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminHODepartmentServiceImpl implements AdminHODepartmentService {

    private final AdminHODepartmentRepository adminHODepartmentRepository;

    @Override
    public List<AdminHODepartmentResponse> getDepartmentList(String value, String campusCode) {
        return adminHODepartmentRepository.getDepartmentList(value, campusCode);
    }

}
