package com.portalevent.core.approver.eventwaitingapproval.controller;

import com.portalevent.core.admin.semestermanagement.service.AdminerSemesterService;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.exportconfig.AdminHExcelEventGenerator;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHSDRequest;
import com.portalevent.core.approver.eventwaitingapproval.exportconfig.ExcelEventGenerator;
import com.portalevent.core.approver.eventwaitingapproval.exportconfig.ExcelParticipantGenerator;
import com.portalevent.core.approver.eventwaitingapproval.exportconfig.ExcelParticipantLecturerGenerator;
import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaCommentEventDetailRequest;
import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaEventListRequest;
import com.portalevent.core.approver.eventwaitingapproval.model.respone.AewaEventListResponse;
import com.portalevent.core.approver.eventwaitingapproval.service.AewaEventExcelService;
import com.portalevent.core.approver.eventwaitingapproval.service.AewaEventService;
import com.portalevent.core.approver.eventwaitingapproval.service.AewaParticipantExcelService;
import com.portalevent.core.approver.eventwaitingapproval.service.AewaParticipantLecturerExcelService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.entity.Semester;
import com.portalevent.infrastructure.constant.Constants;
import com.portalevent.infrastructure.session.PortalEventsSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_EVENT_WAITING_APPROVAL)
@RequiredArgsConstructor
public class AewaEventWaitingApprovalController {

    private final AewaEventService apEventService;

    private final AewaEventExcelService aewaEventExcelService;

    private final AewaParticipantExcelService aewaParticipantExcelService;

    private final AewaParticipantLecturerExcelService aewaParticipantLecturerExcelService;

    private final PortalEventsSession portalEventsSession;

    @PostMapping("/list-event-waiting-approve")
    public PageableObject<AewaEventListResponse> getListEventNotApproved(@RequestBody AewaEventListRequest request) {
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

    @PostMapping("/detail-comment-event")
    public PageableObject getCommentEventById(@RequestBody AewaCommentEventDetailRequest request) {
        return apEventService.getCommentEventById(request);
    }

    @GetMapping("/export/participants")
    public ResponseEntity<Resource> exportParticipants(@RequestParam String semesterName) {
        ExcelParticipantGenerator excelParticipantGenerator = new ExcelParticipantGenerator();
        excelParticipantGenerator.writeHeader();
        excelParticipantGenerator.writeData(aewaParticipantExcelService
                .findAllParticipant(portalEventsSession.getCurrentSubjectCode(), portalEventsSession.getCurrentTrainingFacilityCode(), semesterName));
        UUID uuid = UUID.randomUUID();
        String headerValues = "attachment;filename=" + uuid + ".xlsx";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            excelParticipantGenerator.getWorkbook().write(outputStream);
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
    public ResponseEntity<Resource> exportParticipantsLecturer(@RequestParam String semesterName) {
        ExcelParticipantLecturerGenerator excelParticipantLecturerGenerator = new ExcelParticipantLecturerGenerator();
        excelParticipantLecturerGenerator.writeHeader();
        excelParticipantLecturerGenerator.writeData(aewaParticipantLecturerExcelService
                .findAllParticipantLecturer(portalEventsSession.getCurrentSubjectCode(), portalEventsSession.getCurrentTrainingFacilityCode(), semesterName));
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
    public ResponseEntity<Resource> exportEvent(@RequestParam String idSemester) throws ParseException {
        ExcelEventGenerator excelEventGenerator = new ExcelEventGenerator();
        excelEventGenerator.writeHeader();
        excelEventGenerator.writeData(aewaEventExcelService
                .findAllEventExcel(idSemester));
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
