package com.portalevent.core.adminh.semestermanagement.service;

import com.portalevent.core.adminh.semestermanagement.model.request.AdminHSemesterRequest;
import com.portalevent.core.adminh.semestermanagement.model.respone.AdminHewaSemesterResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.Semester;
import jakarta.validation.Valid;

import java.util.List;

public interface AdminHewaSemesterService {

    List<Semester> getAll1();

    PageableObject<AdminHewaSemesterResponse> getListSemester(Integer page, String request);

    Semester getOne(String id);

    Semester add(@Valid AdminHSemesterRequest semester);

    Semester update(@Valid AdminHSemesterRequest semester, String id);

    boolean remove(String id);
}
