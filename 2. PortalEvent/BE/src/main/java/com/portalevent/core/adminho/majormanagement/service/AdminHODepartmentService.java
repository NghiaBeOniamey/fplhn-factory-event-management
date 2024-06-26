package com.portalevent.core.adminho.majormanagement.service;

import com.portalevent.core.adminho.majormanagement.model.request.AdminHOSearchDepartmentsRequest;
import com.portalevent.core.adminho.majormanagement.model.response.AdminHODepartmentResponse;

import java.util.List;

public interface AdminHODepartmentService {

    List<AdminHODepartmentResponse> getDepartmentList(String value, String campusCode);

}
