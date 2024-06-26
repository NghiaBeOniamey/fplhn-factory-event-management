package fplhn.udpm.identity.core.viewer.controller;

import fplhn.udpm.identity.core.viewer.model.request.MailRequest;
import fplhn.udpm.identity.core.viewer.service.MailService;
import fplhn.udpm.identity.infrastructure.constant.ApiConstant;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstant.API_SUPPORT_MAIL_PREFIX)
@CrossOrigin("*")
@Slf4j
@Hidden
public class MailSupportController {

    private final MailService mailService;

    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@ModelAttribute MailRequest mailRequest) throws MessagingException, IOException {
        try {
            if (mailRequest.getFile() != null) {
                mailService.sendEmailWithAttachment(mailRequest.getSubject(), mailRequest.getBody(), mailRequest.getFile(), mailRequest.getModuleAddress());
            } else {
                mailService.sendEmail(mailRequest.getSubject(), mailRequest.getBody(), mailRequest.getModuleAddress());
            }
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (MessagingException | IOException e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
