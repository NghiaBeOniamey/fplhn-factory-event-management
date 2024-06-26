package com.portalevent.core.adminho.eventattendancelist.controller;

import com.portalevent.core.adminho.eventattendancelist.exportconfig.ExcelAttendanceGenerator;
import com.portalevent.core.adminho.eventattendancelist.model.request.AdminHOEventAttendanceSearchRequest;
import com.portalevent.core.adminho.eventattendancelist.service.AdminHOEventAttendanceService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_HO_ATTENDANCE_LIST)

public class AminHOEventAttendanceController {
    @Autowired
    private AdminHOEventAttendanceService service;

    @PostMapping
    public ResponseObject getAllAttendance(@RequestBody AdminHOEventAttendanceSearchRequest request) {
        return new ResponseObject(service.getAllAttendance(request));
    }

    @PostMapping("/export-attendance")
    public ResponseEntity<Resource> exportAttendance(@RequestParam String idEvent) throws ParseException {
        ExcelAttendanceGenerator excelRegistrationGenerator = new ExcelAttendanceGenerator();
        excelRegistrationGenerator.writeHeader();
        excelRegistrationGenerator.writeData(service
                .getAttendanceList(idEvent));
        UUID uuid = UUID.randomUUID();
        String headerValues = "attachment;filename=" + uuid + ".xlsx";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            excelRegistrationGenerator.getWorkbook().write(outputStream);
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
