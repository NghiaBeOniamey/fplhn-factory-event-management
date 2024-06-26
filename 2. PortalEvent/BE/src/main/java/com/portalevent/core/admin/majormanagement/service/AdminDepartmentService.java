package com.portalevent.core.admin.majormanagement.service;

import com.portalevent.core.admin.majormanagement.model.response.AdminDepartmentResponse;

import java.util.List;

public interface AdminDepartmentService {

    List<AdminDepartmentResponse> getDepartmentList(String value);

}
