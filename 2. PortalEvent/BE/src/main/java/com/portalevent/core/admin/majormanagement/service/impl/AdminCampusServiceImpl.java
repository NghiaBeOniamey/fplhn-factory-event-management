package com.portalevent.core.admin.majormanagement.service.impl;

import com.portalevent.core.admin.majormanagement.model.response.AdminCampusResponse;
import com.portalevent.core.admin.majormanagement.repository.AdminCampusRepository;
import com.portalevent.core.admin.majormanagement.service.AdminCampusService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminCampusServiceImpl implements AdminCampusService {

    private final AdminCampusRepository adminCampusRepository;

    public AdminCampusServiceImpl(AdminCampusRepository adminCampusRepository) {
        this.adminCampusRepository = adminCampusRepository;
    }

    @Override
    public List<AdminCampusResponse> getCampusList() {
        return adminCampusRepository.getCampusList();
    }
}
