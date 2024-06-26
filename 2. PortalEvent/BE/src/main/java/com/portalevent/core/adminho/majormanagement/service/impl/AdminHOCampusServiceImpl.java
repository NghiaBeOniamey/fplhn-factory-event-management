package com.portalevent.core.adminho.majormanagement.service.impl;

import com.portalevent.core.adminho.majormanagement.model.response.AdminHOCampusResponse;
import com.portalevent.core.adminho.majormanagement.repository.AdminHOCampusRepository;
import com.portalevent.core.adminho.majormanagement.service.AdminHOCampusService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHOCampusServiceImpl implements AdminHOCampusService {

    @Autowired
    private AdminHOCampusRepository adminHOCampusRepository;

    @Override
    public List<AdminHOCampusResponse> getCampusList() {
        return adminHOCampusRepository.getCampusList();
    }
}
