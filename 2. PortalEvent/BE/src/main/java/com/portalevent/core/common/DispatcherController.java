package com.portalevent.core.common;

import com.portalevent.core.participant.home.service.PhHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author SonPT
 */

@Controller
public class DispatcherController {

    @Autowired
    private PhHomeService homeService;

    @GetMapping("/home")
    public ResponseEntity<Resource> viewHome() {
        Resource resource = new ClassPathResource("/static/index.html");
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(resource);
    }
    @GetMapping("/event-detail*")
    public ResponseEntity<Resource> viewDetail() {
        Resource resource = new ClassPathResource("/static/detail.html");
        return ResponseEntity.ok()
                .contentType(MediaType.TEXT_HTML)
                .body(resource);
    }

    @GetMapping("/a")
    public String check() {
        return "check";
    }

}
