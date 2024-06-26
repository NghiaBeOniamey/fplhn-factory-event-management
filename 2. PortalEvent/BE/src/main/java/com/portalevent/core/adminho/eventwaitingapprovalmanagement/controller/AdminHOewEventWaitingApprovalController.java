package com.portalevent.core.adminho.eventwaitingapprovalmanagement.controller;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.exportconfig.AdminHOExcelEventGenerator;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.exportconfig.AdminHOExcelParticipantGenerator;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.exportconfig.AdminHOExcelParticipantLecturerGenerator;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOSCDRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOewCommentEventDetailRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOewEventListRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.AdminHOeeEventExcelService;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.AdminHOewEventService;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.AdminHOpParticipantExcelService;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.AdminHOpParticipantLecturerExcelService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
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
@RequestMapping(Constants.UrlPath.URL_API_ADMIN_HO_EVENT_WAITING_APPROVAL)
@RequiredArgsConstructor
public class AdminHOewEventWaitingApprovalController {


    private final AdminHOewEventService apEventService;

    private final AdminHOpParticipantExcelService adParticipantExcelService;

    private final AdminHOpParticipantLecturerExcelService adpParticipantLecturerExcelService;

    private final AdminHOeeEventExcelService adeeEventExcelService;

    @PostMapping("/list-event-waiting-approve")
    public PageableObject getListEventNotApproved(@RequestBody AdminHOewEventListRequest request) {
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
    public PageableObject getCommentEventById(@RequestBody AdminHOewCommentEventDetailRequest request) {
        return apEventService.getCommentEventById(request);
    }

    @GetMapping("/export/participants")
    public ResponseEntity<Resource> exportParticipants(@RequestParam(required = false) String subjectCode) {
        AdminHOExcelParticipantGenerator excelGenerator = new AdminHOExcelParticipantGenerator();
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
        AdminHOExcelParticipantLecturerGenerator excelParticipantLecturerGenerator = new AdminHOExcelParticipantLecturerGenerator();
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
    public ResponseEntity<Resource> exportEvent(AdminHOSCDRequest adminHOSCDRequest) throws ParseException {
        AdminHOExcelEventGenerator excelEventGenerator = new AdminHOExcelEventGenerator();
        excelEventGenerator.writeHeader();
        excelEventGenerator.writeData(adeeEventExcelService.findAllEventExcel(adminHOSCDRequest));
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

//    @GetMapping("/parent-major")
//    public List<AdminerMajorResponse> getAll() {
//        return ammMajorService.getAllMajors(portalEventsSession.getCurrentTrainingFacilityCode());
//    }

}
