package com.portalevent.core.participant.home.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.participant.home.model.request.PhDeleteCommentRequest;
import com.portalevent.core.participant.home.model.request.PhPostCommentRequest;
import com.portalevent.core.participant.home.model.request.PhReplyCommentRequest;
import com.portalevent.core.participant.home.model.request.PhRollCallRequest;
import com.portalevent.core.participant.home.model.response.PhEventScheduleResponse;
import com.portalevent.core.participant.home.service.PhHomeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author SonPT
 */

@RestController

@RequestMapping("/api/home")
public class PhHomeController {

    private final PhHomeService homeService;

    public PhHomeController(PhHomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping("/get-all")
    public ResponseObject getEventSchedule() {
        List<PhEventScheduleResponse> result = homeService.getAll();
        return new ResponseObject(result);
    }

    @GetMapping("/event-detail/{id}")
    public ResponseObject detailEvent(@PathVariable String id) {
        return new ResponseObject(homeService.eventDetail(id));
    }

    @GetMapping("/get-event-coming-up")
    public ResponseObject getEventComingUp() {
        return new ResponseObject(homeService.getEventComingUp());
    }

    @PostMapping("/register-event")
    public ResponseObject registerEvent(@RequestParam String eventId) {
        return new ResponseObject(homeService.registerEvent(eventId));
    }

    @PostMapping("/roll-call")
    public ResponseObject rollCall(@RequestParam String eventId) {
        return new ResponseObject(homeService.rollCall(eventId));
    }

    @GetMapping("/check-roll-call")
    public ResponseObject checkRollCall(@RequestParam String eventId) {
        return new ResponseObject(homeService.checkRollCall(eventId));
    }

    @GetMapping("/get-list-organizer")
    public ResponseObject getListOrganizer() {
        return new ResponseObject(homeService.listOrganizer());
    }

    @PostMapping("/post-comment")
    public ResponseObject postComment(@RequestBody PhPostCommentRequest req) {
        return new ResponseObject(homeService.postComment(req));
    }

    @GetMapping("/get-comment/{eventId}")
    public ResponseObject getComment(@PathVariable("eventId") String eventId, @RequestParam("pageNumber") int pageNumber) {
        return new ResponseObject(homeService.getComment(eventId, pageNumber));
    }

    @DeleteMapping("/delete-comment")
    public ResponseObject deleteComment(@RequestBody PhDeleteCommentRequest req) {
        return new ResponseObject(homeService.deleteComment(req));
    }

    @PostMapping("/reply-comment")
    public ResponseObject replyComment(@RequestBody PhReplyCommentRequest req) {
        return new ResponseObject(homeService.replyComment(req));
    }

    @GetMapping("/check-register-to-participate")
    public ResponseObject checkRegisterToParticipate(@RequestParam String eventId) {
        return new ResponseObject(homeService.checkRegisterToParticipate(eventId));
    }

}
