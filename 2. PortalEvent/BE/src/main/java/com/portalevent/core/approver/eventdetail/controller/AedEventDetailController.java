package com.portalevent.core.approver.eventdetail.controller;

import com.portalevent.core.approver.eventdetail.model.request.AedDeleteCommentRequest;
import com.portalevent.core.approver.eventdetail.model.request.AedPostCommentRequest;
import com.portalevent.core.approver.eventdetail.model.request.AedReplyCommentRequest;
import com.portalevent.core.approver.eventdetail.service.AedEventDetailService;
import com.portalevent.core.approver.eventwaitingapproval.model.request.AewaEventApproveRequest;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(Constants.UrlPath.URL_API_APPROVER_EVENT_DETAIL)

public class AedEventDetailController {
    @Autowired
    private AedEventDetailService aedEventDetailService;

    @PutMapping("/approve-event")
    public ResponseObject approvalEvent(@RequestBody AewaEventApproveRequest req) {
        return aedEventDetailService.approvalEvent(req);
    }

    @GetMapping("/waiting-approval/detail/{id}")
    public ResponseObject getEventApprovedDetail(@PathVariable("id") String id) {
        return new ResponseObject(aedEventDetailService.getDetailEventApproved(id));
    }

    @GetMapping("/get-comment/{eventId}")
    public ResponseObject getComment(@PathVariable("eventId") String eventId, @RequestParam("pageNumber") int pageNumber) {
        return new ResponseObject(aedEventDetailService.getComment(eventId, pageNumber));
    }

    @GetMapping("/get-evidence/{idEvent}")
    public ResponseObject getEvidence(@PathVariable("idEvent") String id) {
        return new ResponseObject(aedEventDetailService.getEvidenceByIdEvent(id));
    }

    @PostMapping("/post-comment")
    public ResponseObject postComment(@RequestBody AedPostCommentRequest req) {
        return new ResponseObject(aedEventDetailService.postComment(req));
    }

    @DeleteMapping("/delete-comment")
    public ResponseObject deleteComment(@RequestBody AedDeleteCommentRequest req) {
        return new ResponseObject(aedEventDetailService.deleteComment(req));
    }

    @PostMapping("/reply-comment")
    public ResponseObject replyComment(@RequestBody AedReplyCommentRequest req) {
        return new ResponseObject(aedEventDetailService.replyComment(req));
    }

    @GetMapping("/get-agenda-item/{eventId}")
    public ResponseObject getAgendaItem(@PathVariable("eventId") String eventId) {
        return new ResponseObject(aedEventDetailService.getListAgendaItemByEventId(eventId));
    }

    @GetMapping("/get-resource/{eventId}")
    public ResponseObject getResourceEvent(@PathVariable("eventId") String eventId) {
        return new ResponseObject(aedEventDetailService.getResourcesByEventId(eventId));
    }

    @GetMapping("/get-location/{eventId}")
    public ResponseObject getLocationEvent(@PathVariable("eventId") String eventId) {
        return new ResponseObject(aedEventDetailService.getLocationByEventId(eventId));
    }

    @GetMapping("/get-object/{eventId}")
    public ResponseObject getObjectEvent(@PathVariable("eventId") String eventId) {
        return new ResponseObject(aedEventDetailService.getObjectByEventId(eventId));
    }

    @GetMapping("/get-major/{eventId}")
    public ResponseObject getMajorByIdEvent(@PathVariable("eventId") String id) {
        return new ResponseObject(aedEventDetailService.getMajorByIdEvent(id));
    }

    @PutMapping("/approver-periodic-event")
    public ResponseObject approverPeriodicEvent(@RequestParam("id") String id) {
        return new ResponseObject(aedEventDetailService.approverPeriodicEvent(id));
    }

    @PutMapping("/no-approver-periodic-event")
    public ResponseObject noApproverPeriodicEvent(@RequestParam("id") String id, @RequestParam("reason") String reason) {
        return new ResponseObject(aedEventDetailService.noApproverPeriodicEvent(id, reason));
    }

    @GetMapping("/get-list-organizer/{eventId}")
    public ResponseObject getListOrganizerByIdEvent(@PathVariable("eventId") String id) {
        return new ResponseObject(aedEventDetailService.getListOrganizerByIdEvent(id));
    }

    @GetMapping("/get-event-in-time")
    public ResponseObject getNameEventsInTime(@RequestParam("startTime") Long startTime,
                                           @RequestParam("endTime") Long endTime,
                                           @RequestParam("id") String id) {
        return new ResponseObject(aedEventDetailService.getNameEventsInTime(id, startTime, endTime));
    }

}
