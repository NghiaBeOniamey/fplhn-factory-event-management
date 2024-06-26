package com.portalevent.core.adminho.semestermanagement.controller;

import com.portalevent.core.adminho.semestermanagement.model.request.AdminHOSemesterRequest;
import com.portalevent.core.adminho.semestermanagement.service.AdminHOewSemesterService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_HO_SEMESTER_MANAGEMENT)

public class AdminHOSemesterController {

    private final AdminHOewSemesterService service;

    public AdminHOSemesterController(AdminHOewSemesterService service) {
        this.service = service;
    }

    @GetMapping("/list-semester")
    public ResponseObject getListEventApproved(@RequestParam(defaultValue = "0",name = "page") Integer page, @RequestParam("searchName") String searchName) {
        return new ResponseObject(service.getListSemester(page, searchName));
    }

    @PostMapping("/add")
    public ResponseObject addSemester(@RequestBody AdminHOSemesterRequest createRequest) {
        return new ResponseObject(service.add(createRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateSemester(@RequestBody AdminHOSemesterRequest updateRequest, @PathVariable("id") String id) {
        return new ResponseObject(service.update(updateRequest, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteSemester(@PathVariable("id") String id) {
        return new ResponseObject(service.remove(id));
    }

}
