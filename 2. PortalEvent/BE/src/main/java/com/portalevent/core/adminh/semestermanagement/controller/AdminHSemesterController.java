package com.portalevent.core.adminh.semestermanagement.controller;

import com.portalevent.core.adminh.semestermanagement.model.request.AdminHSemesterRequest;
import com.portalevent.core.adminh.semestermanagement.service.AdminHewaSemesterService;
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
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_H_SEMESTER_MANAGEMENT)

public class AdminHSemesterController {

    private final AdminHewaSemesterService service;

    public AdminHSemesterController(AdminHewaSemesterService service) {
        this.service = service;
    }

    @GetMapping("/list-semester")
    public ResponseObject getListEventApproved(@RequestParam(defaultValue = "0", name = "page") Integer page, @RequestParam("searchName") String searchName) {
        return new ResponseObject(service.getListSemester(page, searchName));
    }

    @PostMapping("/add")
    public ResponseObject addSemester(@RequestBody AdminHSemesterRequest createRequest) {
        return new ResponseObject(service.add(createRequest));
    }

    @PutMapping("/update/{id}")
    public ResponseObject updateSemester(@RequestBody AdminHSemesterRequest updateRequest, @PathVariable("id") String id) {
        return new ResponseObject(service.update(updateRequest, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseObject deleteSemester(@PathVariable("id") String id) {
        return new ResponseObject(service.remove(id));
    }

}
