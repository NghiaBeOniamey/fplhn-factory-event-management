package com.portalevent.core.admin.eventwaitingapprovalmanagement.controller;

import com.portalevent.core.admin.eventwaitingapprovalmanagement.exportconfig.AdminerExcelEventGenerator;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.exportconfig.AdminerExcelParticipantGenerator;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.exportconfig.AdminerExcelParticipantLecturerGenerator;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerCommentEventDetailRequest;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerEventListRequest;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerSDRequest;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.service.AdminerEventExcelService;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.service.AdminerEventService;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.service.AdminerParticipantExcelService;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.service.AdminerParticipantLecturerExcelService;
import com.portalevent.core.admin.semestermanagement.service.AdminerSemesterService;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.exportconfig.AdminHExcelEventGenerator;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHSDRequest;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Semester;
import com.portalevent.infrastructure.constant.Constants;
import com.portalevent.infrastructure.session.PortalEventsSession;
import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_EVENT_WAITING_APPROVAL)
@RequiredArgsConstructor
public class AdminerEventWaitingApprovalController {

    private final AdminerEventService apEventService;

    private final AdminerParticipantExcelService adParticipantExcelService;

    private final AdminerParticipantLecturerExcelService adpParticipantLecturerExcelService;

    private final AdminerEventExcelService adeeEventExcelService;

    private final PortalEventsSession portalEventsSession;

    @PostMapping("/list-event-waiting-approve")
    public PageableObject getListEventNotApproved(@RequestBody AdminerEventListRequest request) {
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
    public ResponseObject getEventDepartment(){
        return apEventService.getEventDepartment();
    }

    @PostMapping("/detail-comment-event")
    public PageableObject getCommentEventById(@RequestBody AdminerCommentEventDetailRequest request) {
        return apEventService.getCommentEventById(request);
    }

    @GetMapping("/export/participants")
    public ResponseEntity<Resource> exportParticipants(
            @RequestParam String subjectCode,
            @RequestParam String semesterName) {
        AdminerExcelParticipantGenerator excelGenerator = new AdminerExcelParticipantGenerator();
        excelGenerator.writeHeader();
        excelGenerator.writeData(adParticipantExcelService
                .findAllParticipant(subjectCode, portalEventsSession.getCurrentTrainingFacilityCode(), semesterName));
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
    public ResponseEntity<Resource> exportParticipantsLecturer(
            @RequestParam String subjectCode,
            @RequestParam String semesterName) {
        AdminerExcelParticipantLecturerGenerator excelParticipantLecturerGenerator = new AdminerExcelParticipantLecturerGenerator();
        excelParticipantLecturerGenerator.writeHeader();
        excelParticipantLecturerGenerator.writeData(adpParticipantLecturerExcelService
                .findAllParticipantLecturer(subjectCode, portalEventsSession.getCurrentTrainingFacilityCode(), semesterName));
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
    public ResponseEntity<Resource> exportEvent(AdminerSDRequest adminerSDRequest) throws ParseException {
        AdminerExcelEventGenerator excelEventGenerator = new AdminerExcelEventGenerator();
        excelEventGenerator.writeHeader();
        excelEventGenerator.writeData(adeeEventExcelService.findAllEventExcel(adminerSDRequest));
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
