package com.portalevent.core.organizer.eventDetail.controller;

import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaEventApproveRequest;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.eventDetail.exportconfig.OedExcelEventDetailGenerator;
import com.portalevent.core.organizer.eventDetail.model.request.OedAddEventLocationRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedAttendanceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateAgendaRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateEventOrganizerRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedCreateResourceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedDeleteCommentRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedDeleteEventOrganizerRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedEventCloseRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedEvidenceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedInvitationTimeRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedPostCommentRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedRegisterRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedReplyCommentRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedSendMailEvidenceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateAgendaRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateEventLocationRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateEventOrganizer;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateEventRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateNumberParticipantRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedUpdateResourceRequest;
import com.portalevent.core.organizer.eventDetail.model.request.OedWaitApprovalPeriodicallyRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventOrganizerCustom;
import com.portalevent.core.organizer.eventDetail.service.OedEventDetailService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_EVENT_DETAIL)
public class OedEventDetailController {

    private final OedEventDetailService eventDetailService;

    public OedEventDetailController(OedEventDetailService eventDetailService) {
        this.eventDetailService = eventDetailService;
    }

    @GetMapping("/{eventId}")
    public ResponseObject detail(@PathVariable("eventId") String id) {
        return new ResponseObject(eventDetailService.detail(id));
    }

    @GetMapping("/get-all-semester")
    public ResponseObject getSemesters() {
        return new ResponseObject(eventDetailService.getSemesters());
    }

    @GetMapping("/get-object/{idEvent}")
    public ResponseObject getObjectByIdEvent(@PathVariable("idEvent") String id) {
        return new ResponseObject(eventDetailService.getObjectByIdEvent(id));
    }

    @PutMapping("/update-status-event/{idEvent}")
    public ResponseObject updateStatusEvent(@PathVariable("idEvent") String id) {
        return new ResponseObject(eventDetailService.updateStatusEventByIdEvent(id));
    }

    @GetMapping("/get-all-major")
    public ResponseObject getMajors() {
        return new ResponseObject(eventDetailService.getMajors());
    }

    @GetMapping("/get-major/{idEvent}")
    public ResponseObject getMajorByIdEvent(@PathVariable("idEvent") String id) {
        return new ResponseObject(eventDetailService.getMajorByIdEvent(id));
    }

    @GetMapping("/get-major-campus/{idEvent}")
    public ResponseObject getMajorCampusByIdEvent(@PathVariable("idEvent") String id) {
        return new ResponseObject(eventDetailService.getMajorCampusByIdEvent(id));
    }

    @GetMapping("/get-major-campus-by-idDepartment/{idDepartment}")
    public ResponseObject getMajorCampusBy(@PathVariable String idDepartment) {
        return new ResponseObject(eventDetailService.getAllMajorCampus(idDepartment));
    }

    @GetMapping("/get-department-campus")
    public ResponseObject getDepartmentCampus() {
        return new ResponseObject(eventDetailService.getAllDepartmentCampus());
    }

    @GetMapping("/get-department-campus/{idEvent}")
    public ResponseObject getDepartmentCampusByIdEvent(@PathVariable String idEvent) {
        return new ResponseObject(eventDetailService.getAllDepartmentCampusByIdEvent(idEvent));
    }

    @GetMapping("/get-event-location/{idEvent}")
    public ResponseObject getEventLocationByIdEvent(@PathVariable("idEvent") String id) {
        return new ResponseObject(eventDetailService.getEventLocationByIdEvent(id));
    }

    @PutMapping("/update-event")
    public ResponseObject eventUpdate(@RequestBody OedUpdateEventRequest request) {
        return new ResponseObject(eventDetailService.update(request));
    }

    @PutMapping("/upload-image/{id}")
    public ResponseObject uploadImage(@RequestParam("file") MultipartFile file,
                                      @RequestParam("type") int type,
                                      @PathVariable("id") String id) {
        return new ResponseObject(eventDetailService.uploadImage(file, type, id));
    }

    @DeleteMapping("/delete-image/{id}")
    public ResponseObject deleteImage(@PathVariable("id") String id) {
        return new ResponseObject(eventDetailService.deleteImage(id));
    }


    @PutMapping("/update-event-location")
    public ResponseObject updateEventLocation(@RequestBody OedUpdateEventLocationRequest request) {
        return new ResponseObject(eventDetailService.updateEventLocation(request));
    }

    @PostMapping("/add-event-location")
    public ResponseObject addEventLocation(@RequestBody OedAddEventLocationRequest request) {
        return new ResponseObject(eventDetailService.addEventLocation(request));
    }

    @GetMapping("/get-all-category")
    public ResponseObject getAllCategory() {
        return new ResponseObject(eventDetailService.getAll());
    }

    @GetMapping("/get-all-resource/{id}")
    public ResponseObject getAllResource(@PathVariable("id") String idEvent) {
        return new ResponseObject(eventDetailService.getAllResource(idEvent));
    }

    @PostMapping("/create-resource")
    public ResponseObject create(@RequestBody OedCreateResourceRequest request) {
        return new ResponseObject(eventDetailService.create(request));
    }

    @PutMapping("/update-resource")
    public ResponseObject update(@RequestBody OedUpdateResourceRequest request) {
        return new ResponseObject(eventDetailService.update(request));
    }

    @DeleteMapping("/delete-resource/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(eventDetailService.deleteResource(id));
    }

    @GetMapping("/get-list-organizer/{eventId}")
    public ResponseObject getListOrganizer(@PathVariable("eventId") String id) {
        return new ResponseObject(eventDetailService.getAllOrganizerByIdEvent(id));
    }

    @GetMapping("/check-organizer-role/{eventId}")
    public ResponseObject checkOrganizerRole(@PathVariable("eventId") String eventId) {
        return new ResponseObject(eventDetailService.checkOrganizerRole(eventId));
    }

    @GetMapping("/check-organizer-role-co-host/{eventId}")
    public ResponseObject checkOrganizerRoleHasEdit(@PathVariable("eventId") String eventId) {
        return new ResponseObject(eventDetailService.checkOrganizerRoleHasEdit(eventId));
    }

    @DeleteMapping("/delete-event-location/{idEvent}")
    public ResponseObject deleteEventLocationByIdEvent(@PathVariable("idEvent") String id) {
        return new ResponseObject(eventDetailService.deleteEventLocationByIdEvent(id));
    }

    @GetMapping("/get-list-agenda/{eventId}")
    public ResponseObject getListAgenda(@PathVariable("eventId") String id) {
        return new ResponseObject(eventDetailService.getAllAgendaItem(id));
    }

    @GetMapping("/get-list-organizer-not-in-event/{eventId}")
    public ResponseObject getListOrganizergetListOrganizerNotInEvent(@PathVariable("eventId") String id) {
        return new ResponseObject(eventDetailService.getAllOrganizerNotInEvent(id));
    }

    @PostMapping("/create-event-organizer")
    public ResponseObject createEventOrganizer(@RequestBody OedCreateEventOrganizerRequest request) {
        return new ResponseObject(eventDetailService.createEventOrganizer(request));
    }

    @GetMapping("/export-event-organizer/{eventId}")
    public ResponseEntity<Resource> exportEventOrganizer(@PathVariable("eventId") String idEvent) throws ParseException {
        OedExcelEventDetailGenerator exportEventOrganizer = new OedExcelEventDetailGenerator();
        exportEventOrganizer.writeHeader();
        exportEventOrganizer.writeData(eventDetailService.getAllOrganizerByIdEvent(idEvent));
        UUID uuid = UUID.randomUUID();
        String headerValues = "attachment;filename=" + uuid + ".xlsx";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            exportEventOrganizer.getWorkbook().write(outputStream);
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/export-template-event-organizer")
    public ResponseEntity<Resource> exportTemplateEventOrganizer() throws ParseException {
        OedExcelEventDetailGenerator exportEventOrganizer = new OedExcelEventDetailGenerator();
        exportEventOrganizer.writeHeader();
//        exportEventOrganizer.writeValueDefault();
        UUID uuid = UUID.randomUUID();
        String headerValues = "attachment;filename=" + uuid + ".xlsx";
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            exportEventOrganizer.getWorkbook().write(outputStream);
            ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValues)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/import-event-organizer")
    public ResponseObject importEventOrganizer(@RequestPart("eventId") String eventId,  // Phần chứa chuỗi (string)
                                               @RequestPart("file") MultipartFile file) {  // Phần chứa file) {
        return new ResponseObject(eventDetailService.importEventOrganizer(eventId, file));
    }

    @PutMapping("/update-event-organizer")
    public ResponseObject updateEventOrganizer(@RequestBody OedUpdateEventOrganizer request) {
        return new ResponseObject(eventDetailService.updateEventOrganizer(request));
    }

    @DeleteMapping("/delete-event-organizer")
    public ResponseObject deleteEventOrganizer(@RequestBody OedDeleteEventOrganizerRequest request) {
        return new ResponseObject(eventDetailService.deleteEventOrganizer(request));
    }

    @PostMapping("/create-agenda")
    public ResponseObject createAgenda(@RequestBody OedCreateAgendaRequest request) {
        return new ResponseObject(eventDetailService.createAgenda(request));
    }

    @PutMapping("/update-agenda")
    public ResponseObject updateAgenda(@RequestBody OedUpdateAgendaRequest request) {
        return new ResponseObject(eventDetailService.updateAgenda(request));
    }

    @DeleteMapping("/delete-agenda")
    public ResponseObject deleteAgenda(@RequestParam("id") String id) {
        return new ResponseObject(eventDetailService.deleteAgenda(id));
    }

    @GetMapping("/get-all-object")
    public ResponseObject getAllObject() {
        return new ResponseObject(eventDetailService.getAllObject());
    }

    @GetMapping("/get-comment/{eventId}")
    public ResponseObject getComment(@PathVariable("eventId") String eventId, @RequestParam("pageNumber") int pageNumber) {
        return new ResponseObject(eventDetailService.getComment(eventId, pageNumber));
    }

    @PostMapping("/post-comment")
    public ResponseObject postComment(@RequestBody OedPostCommentRequest req) {
        return new ResponseObject(eventDetailService.postComment(req));
    }

    @DeleteMapping("/delete-comment")
    public ResponseObject deleteComment(@RequestBody OedDeleteCommentRequest req) {
        return new ResponseObject(eventDetailService.deleteComment(req));
    }

    @PostMapping("/reply-comment")
    public ResponseObject replyComment(@RequestBody OedReplyCommentRequest req) {
        return new ResponseObject(eventDetailService.replyComment(req));
    }

    @PutMapping("/close-event")
    public ResponseObject closeEvent(@RequestBody OedEventCloseRequest req) {
        return eventDetailService.closeEvent(req);
    }

    @PutMapping("/save_list_agenda")
    public ResponseObject saveListAgenda(@RequestBody List<OedUpdateAgendaRequest> req) {
        return new ResponseObject(eventDetailService.saveListAgenda(req));
    }

    @GetMapping("/get-list-evidence/{eventId}")
    public ResponseObject getEvidences(@PathVariable("eventId") String eventId) {
        return new ResponseObject(eventDetailService.getAllEventEvidence(eventId));
    }

    @PostMapping("/create-evidence")
    public ResponseObject createEvidence(@RequestBody OedEvidenceRequest request) {
        return new ResponseObject(eventDetailService.createEvidence(request));
    }

    @PutMapping("/update-evidence/{idEvidence}")
    public ResponseObject updateEvidence(@PathVariable("idEvidence") String idEvidence, @RequestBody OedEvidenceRequest request) {
        return new ResponseObject(eventDetailService.updateEvidence(idEvidence, request));
    }

    @DeleteMapping("/delete-evidence/{idEvidence}")
    public ResponseObject deleteEvidence(@PathVariable("idEvidence") String idEvidence) {
        return new ResponseObject(eventDetailService.deleteEvidence(idEvidence));
    }

    @PutMapping("/open-register")
    public ResponseObject openOrCloseRegister(@RequestBody OedRegisterRequest request) {
        return new ResponseObject(eventDetailService.openOrCloseRegister(request));
    }

    @PutMapping("/open-attendance")
    public ResponseObject openOrCloseAttendance(@RequestBody OedAttendanceRequest request) {
        return new ResponseObject(eventDetailService.openOrCloseAttendance(request));
    }

    @PostMapping("/add-evidence/{idEvent}")
    public ResponseObject addEvidence(@RequestParam("file") List<MultipartFile> files, @PathVariable("idEvent") String idEvent) {
        return new ResponseObject(eventDetailService.addEvidence(files, idEvent));
    }

    @PutMapping("/update-number-participant")
    public ResponseObject updateNumberParticipant(@RequestBody OedUpdateNumberParticipantRequest request) {
        return new ResponseObject(eventDetailService.updateNumberParticipant(request.getNumberParticipant(), request.getId()));
    }

    @PostMapping("/send-mail-evidence")
    public ResponseObject sendMailEvidence(@RequestBody OedSendMailEvidenceRequest request) {
        return new ResponseObject(eventDetailService.sendMailEvidence(request));
    }

    @PutMapping("/approve-periodic-event")
    public ResponseObject approvePeriodicEvent(@RequestBody OedWaitApprovalPeriodicallyRequest request) {
        return new ResponseObject(eventDetailService.approvePeriodicEvents(request));
    }

    @PostMapping("/event_reorganization/{idEvent}")
    public ResponseObject eventReorganization(@PathVariable("idEvent") String idEvent, @RequestBody List<Integer> value) {
        return new ResponseObject(eventDetailService.eventReorganization(idEvent, value));
    }

    @PostMapping("/send_mail_resource/{idEvent}")
    public ResponseObject sendMailResource(@PathVariable("idEvent") String idEvent) {
        return new ResponseObject(eventDetailService.sendMailResource(idEvent));
    }

    @PutMapping("/save_list_invitation_time")
    public ResponseObject saveListInvitationTime(@RequestBody List<OedInvitationTimeRequest> req) {
        return new ResponseObject(eventDetailService.saveListInvitationTime(req));
    }

    @DeleteMapping("/delete-invitation-time/{idInvitation}")
    public ResponseObject deleteInvitationTime(@PathVariable("idInvitation") String idInvitation) {
        return new ResponseObject(eventDetailService.deleteInvitationTime(idInvitation));
    }

    @GetMapping("/get-list-invitation-time/{eventId}")
    public ResponseObject getInvitationTimes(@PathVariable("eventId") String eventId) {
        return new ResponseObject(eventDetailService.getAllInvitationTime(eventId));
    }

//    @GetMapping("/get-honey-category")
//    public ResponseObject getHoneyCategory() {
//        return new ResponseObject(eventDetailService.getHoneyCategory());
//    }

//    @PostMapping("/send-conversion-request")
//    public ResponseObject sendConversionRequest(@RequestBody OedSendConversionRequest request) {
//        return new ResponseObject(eventDetailService.sendConversionRequest(request));
//    }

    @PutMapping("/approve-event")
    public ResponseObject approvalEvent(@RequestBody AewaEventApproveRequest req) {
        return eventDetailService.approvalEvent(req);
    }
}
