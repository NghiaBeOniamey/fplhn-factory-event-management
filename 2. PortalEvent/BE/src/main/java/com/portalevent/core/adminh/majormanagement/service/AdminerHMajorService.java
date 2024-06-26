package com.portalevent.core.adminh.majormanagement.service;

import com.portalevent.core.adminh.majormanagement.model.response.AdminerHMajorResponse;

import java.util.List;

public interface AdminerHMajorService {

    List<AdminerHMajorResponse> getMajorList(String value, String campusCode);

    List<AdminerHMajorResponse> getAllMajors(String campusCode);

    void fetchAndSaveDepartments();

}
