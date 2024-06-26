package com.portalevent.core.adminho.semestermanagement.service;

import com.portalevent.core.adminho.semestermanagement.model.request.AdminHOSemesterRequest;
import com.portalevent.core.adminho.semestermanagement.model.respone.AdminHOewSemesterResponse;
import com.portalevent.core.common.PageableObject;
import com.portalevent.entity.Semester;
import jakarta.validation.Valid;

import java.util.List;

public interface AdminHOewSemesterService {

    List<Semester> getAll1();

    PageableObject<AdminHOewSemesterResponse> getListSemester(Integer page, String request);

    Semester getOne(String id);

    Semester add(@Valid AdminHOSemesterRequest semester);

    Semester update(@Valid AdminHOSemesterRequest semester, String id);

    boolean remove(String id);

}
