package com.portalevent.core.adminh.registrationlistmanagement.controller;

import com.portalevent.core.adminh.registrationlistmanagement.exportconfig.ExcelRegistrationGenerator;
import com.portalevent.core.adminh.registrationlistmanagement.model.request.AdminHEventParticipantRequest;
import com.portalevent.core.adminh.registrationlistmanagement.service.AdminHRegistrationListService;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_H_REGISTRATION_LIST)
public class AdminHRegistrationListController {

    private final AdminHRegistrationListService service;

    public AdminHRegistrationListController(AdminHRegistrationListService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseObject getAll(final AdminHEventParticipantRequest request) {
        return new ResponseObject(service.getListResgistration(request));
    }

    @PostMapping("/export-registration")
    public ResponseEntity<Resource> exportRegistration(@RequestParam String idEvent) throws ParseException {
        ExcelRegistrationGenerator excelRegistrationGenerator = new ExcelRegistrationGenerator();
        excelRegistrationGenerator.writeHeader();
        excelRegistrationGenerator.writeData(service
                .getRegistrationList(idEvent));
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
