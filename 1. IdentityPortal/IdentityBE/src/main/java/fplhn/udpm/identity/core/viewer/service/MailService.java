package fplhn.udpm.identity.core.viewer.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${mail.to.send}")
    private String mailToSend;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String subject, String body, String moduleAddress) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setTo(mailToSend);
            helper.setSubject(subject);

            String htmlBody = "<html><head><style>"
                    + "body {font-family: Arial; color: #333; padding: 20px;}"
                    + "h1 {color: red;}"
                    + "p {line-height: 1.6;}"
                    + "</style></head><body>"
                    + "<h1>Thông báo lỗi từ " + moduleAddress + "!</h1>"
                    + "<p>" + body + "</p>"
                    + "</body></html>";
            helper.setText(htmlBody, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace(System.out);
        }
    }

    public void sendEmailWithAttachment(String subject, String body, MultipartFile file, String module) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(mailToSend);
        helper.setSubject(subject);
        String htmlBody = "<html><head><style>"
                + "body {font-family: Arial; color: #333; padding: 20px;}"
                + "h1 {color: red;}"
                + "p {line-height: 1.6;}"
                + "</style></head><body>"
                + "<h1>Thông báo Lỗi từ " + module + "!</h1>"
                + "<p>" + body + "</p>"
                + "</body></html>";
        helper.setText(htmlBody, true);

        if (file != null && !file.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            InputStreamSource source = new ByteArrayResource(file.getBytes());
            helper.addAttachment(fileName, source);
        }

        mailSender.send(message);
    }

}
