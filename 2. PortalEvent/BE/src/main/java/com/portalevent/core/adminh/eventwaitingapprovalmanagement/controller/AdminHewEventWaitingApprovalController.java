package com.portalevent.core.adminh.eventwaitingapprovalmanagement.controller;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.exportconfig.AdminHExcelEventGenerator;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.exportconfig.AdminHExcelParticipantGenerator;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.exportconfig.AdminHExcelParticipantLecturerGenerator;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHSDRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHewCommentEventDetailRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHewEventListRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.AdminHeeEventExcelService;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.AdminHewEventService;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.AdminHpParticipantExcelService;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.AdminHpParticipantLecturerExcelService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_H_EVENT_WAITING_APPROVAL)
public class AdminHewEventWaitingApprovalController {

    private final AdminHewEventService apEventService;

    private final AdminHpParticipantExcelService adParticipantExcelService;

    private final AdminHpParticipantLecturerExcelService adpParticipantLecturerExcelService;

    private final AdminHeeEventExcelService adeeEventExcelService;

    public AdminHewEventWaitingApprovalController(AdminHewEventService apEventService,
                                                  AdminHpParticipantExcelService adParticipantExcelService,
                                                  AdminHpParticipantLecturerExcelService adpParticipantLecturerExcelService,
                                                  AdminHeeEventExcelService adeeEventExcelService) {
        this.apEventService = apEventService;
        this.adParticipantExcelService = adParticipantExcelService;
        this.adpParticipantLecturerExcelService = adpParticipantLecturerExcelService;
        this.adeeEventExcelService = adeeEventExcelService;
    }

    @PostMapping("/list-event-waiting-approve")
    public PageableObject getListEventNotApproved(@RequestBody AdminHewEventListRequest request) {
        return apEventService.getListEventNotApproved(request);
    }

    @GetMapping("/waiting-approval/detail/{id}")
    public ResponseObject getEventApprovedDetail(@PathVariable("id") String id) {
        return apEventService.getDetailEventApproved(id);
    }

    @GetMapping("/event-category/list")
    public ResponseObject getEventCategory() {
        return apEventService.getEventCategory();
    }

    @GetMapping("/event-major/list")
    public ResponseObject getEventMajor() {
        return apEventService.getEventMajor();
    }

    @GetMapping("/event-department/list")
    public ResponseObject getEventDepartment() {
        return apEventService.getEventDepartment();
    }

    @PostMapping("/detail-comment-event")
    public PageableObject getCommentEventById(@RequestBody AdminHewCommentEventDetailRequest request) {
        return apEventService.getCommentEventById(request);
    }

    @GetMapping("/export/participants")
    public ResponseEntity<Resource> exportParticipants(@RequestParam(required = false) String subjectCode) {
        AdminHExcelParticipantGenerator excelGenerator = new AdminHExcelParticipantGenerator();
        excelGenerator.writeHeader();
        excelGenerator.writeData(adParticipantExcelService.findAllParticipant(subjectCode));
        UUID uuid = UUID.randomUUID();
        String headerValues = "attachment;filename=" + uuid + ".xlsx";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            excelGenerator.getWorkbook().write(outputStream);
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/export/participants-lecturer")
    public ResponseEntity<Resource> exportParticipantsLecturer(@RequestParam(required = false) String subjectCode) {
        AdminHExcelParticipantLecturerGenerator excelParticipantLecturerGenerator = new AdminHExcelParticipantLecturerGenerator();
        excelParticipantLecturerGenerator.writeHeader();
        excelParticipantLecturerGenerator.writeData(adpParticipantLecturerExcelService.findAllParticipantLecturer(subjectCode));
        UUID uuid = UUID.randomUUID();
        String headerValues = "attachment;filename=" + uuid + ".xlsx";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            excelParticipantLecturerGenerator.getWorkbook().write(outputStream);
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/export-event")
    public ResponseEntity<Resource> exportEvent(AdminHSDRequest adminHSDRequest) throws ParseException {
        AdminHExcelEventGenerator excelEventGenerator = new AdminHExcelEventGenerator();
        excelEventGenerator.writeHeader();
        excelEventGenerator.writeData(adeeEventExcelService.findAllEventExcel(adminHSDRequest));
        UUID uuid = UUID.randomUUID();
        String headerValues = "attachment;filename=" + uuid + ".xlsx";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            excelEventGenerator.getWorkbook().write(outputStream);
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
