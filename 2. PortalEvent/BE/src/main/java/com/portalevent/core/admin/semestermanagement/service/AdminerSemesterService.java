package com.portalevent.core.admin.semestermanagement.service;

import com.portalevent.core.admin.semestermanagement.model.request.AdminerSemesterRequest;
import com.portalevent.core.admin.semestermanagement.model.respone.AdminerSemesterResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.Semester;
import jakarta.validation.Valid;

import java.util.List;

public interface AdminerSemesterService {

    List<Semester> getAll();

    PageableObject<AdminerSemesterResponse> getListSemester(Integer page, String request);

    Semester getOne(String id);

    Semester add(@Valid AdminerSemesterRequest semester);

    Semester update(@Valid AdminerSemesterRequest semester, String id);

    boolean remove(String id);

}
