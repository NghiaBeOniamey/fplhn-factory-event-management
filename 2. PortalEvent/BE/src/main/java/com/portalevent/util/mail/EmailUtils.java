package com.portalevent.util.mail;

import com.portalevent.core.organizer.eventDetail.model.response.OedEventLocationResponse;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventOrganizerCustom;
import com.portalevent.infrastructure.constant.MailConstant;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailUtils {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailUtils(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Async
    public void sendEmail(MailRequest request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", request.getTitle()) + request.getBody() + MailConstant.FOOTER;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(request.getMails());
            mimeMessageHelper.setCc(request.getMails());
            mimeMessageHelper.setBcc(request.getMails());
            mimeMessageHelper.setText(htmlBody, true);
            mimeMessageHelper.setSubject("[Portal Event] " + request.getSubject());
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailEvent(MailEventRequest request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String lectuerList = "";
        String contentList = "";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());
            mimeMessageHelper.setSubject(request.getSubject());
            for (String lecturer : request.getLecturer()) {
                lectuerList += "<li>" + lecturer + "</li>";
            }
            for (String content : request.getContent()) {
                contentList += "<li>" + content + "</li>";
            }
            mimeMessageHelper.setText(htmlBody + MailConstant.CONTENT_EVENT_MAIL.replace("{eventName}", request.getEventName())
                    .replace("{lecturerList}", lectuerList)
                    .replace("{contentList}", contentList)
                    .replace("{time}", request.getTime())
                    .replace("{date}", request.getDate())
                    .replace("{typeEvent}", request.getTypeEvent())
                    .replace("{linkZoom}", request.getLinkZoom())
                    .replace("{linkBackground}", request.getLinkBackground()) + MailConstant.FOOTER, true);
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailApprovalEvent(MailRequestApprovalEvent request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String lectuerList = "";
        String contentList = "";
        String locationList = "";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH2);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());
            mimeMessageHelper.setSubject(request.getSubject());
            for (OedEventOrganizerCustom lecturer : request.getLecturers()) {
                lectuerList += "<li>" + lecturer.getName() + " - " + lecturer.getUsername() + "</li>";
            }
            for (String content : request.getContent()) {
                contentList += "<li>" + content + "</li>";
            }
            for (OedEventLocationResponse location : request.getLocations()) {
                if (location.getFormality() == 0) {
                    locationList += "<li> Online - "+ location.getName() + ": <a href='" + location.getPath() + "'>Tại đây</a></li>";
                } else {
                    locationList += "<li> Offline -"+ location.getName() + ": " + location.getPath() + "</li>";
                }
            }
            mimeMessageHelper.setText(htmlBody + MailConstant.CONTENT_APPROVAL_EVENT_MAIL
                    .replace("{eventName}", request.getEventName())
                    .replace("{lecturerList}", lectuerList)
                    .replace("{contentList}", contentList)
                    .replace("{date}", request.getDate())
                    .replace("{typeEvent}", request.getTypeEvent())
                    .replace("{locations}", locationList)
                    .replace("{linkBackground}", request.getLinkBackground())
                    .replace("{linkBanner}", request.getLinkBanner()) + MailConstant.FOOTER, true);
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailCloseEvent(MailCustomRequest request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String objectList = "";
        String lectuerList = "";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());
            for (MailEventOrganizerCustom lecturer : request.getLecturerList()) {
                lectuerList += "<li>" + lecturer.getName() + " - " + lecturer.getUsername() + "</li>";
            }
            for (MailObjectResponse object : request.getObjects()) {
                objectList += "<li>" + object.getName() + "</li>";
            }
            mimeMessageHelper.setText(htmlBody+ MailConstant.CONTENT_CLOSE_EVENT_MAIL
                    .replace("{eventName}", request.getEventName())
                    .replace("{organizerName}", request.getOganizerName())
                    .replace("{date}", request.getDate())
                    .replace("{category}", request.getCategory())
                    .replace("{typeEvent}", request.getTypeEvent())
                    .replace("{lecturerList}", lectuerList)
                    .replace("{objects}", objectList)
                    .replace("{reason}", request.getReason()) + MailConstant.FOOTER, true);
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailAddOrganizer(MailOrgenizer request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String objectList = "";
        String lectuerList = "";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());

            for (MailEventOrganizerCustom lecturer : request.getLecturerList()) {
                lectuerList += "<li>" + lecturer.getName() + " - " + lecturer.getUsername() + "</li>";
            }
            for (MailObjectResponse object : request.getObjects()) {
                objectList += "<li>" + object.getName() + "</li>";
            }
            mimeMessageHelper.setText(htmlBody+ MailConstant.CONTENT_ADD_ORGANIZER_MAIL
                    .replace("{userNameAdd}", request.getUserNameAdd())
                    .replace("{status}", request.getStatus())
                    .replace("{eventName}", request.getEventName())
                    .replace("{date}", request.getDate())
                    .replace("{category}", request.getCategory())
                    .replace("{typeEvent}", request.getTypeEvent())
                    .replace("{lecturerList}", lectuerList)
                    .replace("{objects}", objectList)
                    .replace("{role}", request.getRole()) + MailConstant.FOOTER, true);
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailDeleteOrganizer(MailOrgenizer request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String objectList = "";
        String lectuerList = "";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());

            for (MailEventOrganizerCustom lecturer : request.getLecturerList()) {
                lectuerList += "<li>" + lecturer.getName() + " - " + lecturer.getUsername() + "</li>";
            }
            for (MailObjectResponse object : request.getObjects()) {
                objectList += "<li>" + object.getName() + "</li>";
            }
            mimeMessageHelper.setText(htmlBody+ MailConstant.CONTENT_DELETE_ORGANIZER_MAIL
                    .replace("{userNameAdd}", request.getUserNameAdd())
                    .replace("{eventName}", request.getEventName())
                    .replace("{date}", request.getDate())
                    .replace("{category}", request.getCategory())
                    .replace("{typeEvent}", request.getTypeEvent())
                    .replace("{lecturerList}", lectuerList)
                    .replace("{objects}", objectList) + MailConstant.FOOTER, true);
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailEvidence(MailEvidenceRequest request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String countParticipant = "";
        String numberParticipants = "";
        String tableEvidence = """
                <table style="margin-top: 10px; margin-bottom: 10px;"><tr>
                                    <th>STT</th>
                                    <th>Tên evidence</th>
                                    <th>Đường đẫn</th>
                                    <th>Mô tả</th>
                                </tr>
                """;
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());

            int stt = 1;
            for (MailEvidenceResponce evidence : request.getEvidences()) {
                String description = "";
                if (evidence.getDescription() == null || evidence.getDescription().equals("")) {
                    description = "_";
                } else {
                    description = evidence.getDescription();
                }
                tableEvidence += "<tr>";
                tableEvidence += "<td>" + stt + "</td>";
                tableEvidence += "<td>" + evidence.getName() + "</td>";
                tableEvidence += "<td><a href='" + evidence.getPath() + "'>Tại đây</a></td>";
                tableEvidence += "<td>" + description + "</td>";
                tableEvidence += "</tr>";
                stt++;
            }
            tableEvidence += "</table>";
            if (request.getCountParticipant() == null || request.getCountParticipant().equals("")) {
                countParticipant = "0";
            } else {
                countParticipant = request.getCountParticipant();
            }
            if (request.getNumberParticipants() == null || request.getNumberParticipants().equals("")) {
                numberParticipants = "0";
            } else {
                numberParticipants = request.getNumberParticipants();
            }
            mimeMessageHelper.setText(htmlBody+ MailConstant.CONTENT_EVIDENCE_MAIL
                    .replace("{eventName}", request.getEventName())
                    .replace("{time}", request.getTime())
                    .replace("{date}", request.getDate())
                    .replace("{countParticipant}", countParticipant)
                    .replace("{numberParticipants}", numberParticipants)
                    .replace("{eventType}", request.getEventType())
                    .replace("{percentage}", request.getPercentage())
                    .replace("{evidences}", tableEvidence) + MailConstant.FOOTER, true);
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailResource(MailResourceRequest request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String resourceList = "";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());

            for (MailResourceResponse resourceResponce : request.getResources()) {
                resourceList += "<li>" + resourceResponce.getName() + ": <a href='" + resourceResponce.getPath() + "'>Tại đây</a></li>";
            }

            mimeMessageHelper.setText(htmlBody+ MailConstant.CONTENT_RESOURCE_MAIL
                    .replace("{eventName}", request.getEventName())
                    .replace("{category}", request.getCategory())
                    .replace("{resources}", resourceList) + MailConstant.FOOTER, true);
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailEventUpdateWhenApproved(MailEventUpdateWhenApproved request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String eventList = "";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());
            mimeMessageHelper.setSubject(request.getSubject());
            eventList += """
                        <ul type="circle">
                    """;

            if (request.getContent() != null) {
                eventList += """
                    <li>Nội dung:
                        <ul>
                    """;
                for (String content : request.getContent()) {
                    eventList += "<li>" + content + "</li>";
                }
                eventList += """
                         </ul>
                     </li>
                     """;
            }
            if (request.getCategory() != null) {
                eventList += "<li>Thể loại: " + request.getCategory() + "</li>";
            }
            if (request.getTypeEvent() != null) {
                eventList += "<li>Sự kiện cho: " + request.getTypeEvent() + "</li>";
            }
            if (request.getDate() != null) {
                eventList += "<li>Thời gian: " + request.getDate() + "</li>";
            }
            if (request.getExpectedParticipants() != null ) {
                eventList += "<li>Dự kiến số người tham gia: " + request.getExpectedParticipants() + "</li>";
            }
            if (request.getDepartment() != null) {
                eventList += """
                    <li>bộ môn:
                        <ul>
                    """;
                for (String department : request.getDepartment()) {
                    eventList += "<li>" + department + "</li>";
                }
                eventList += """
                            </ul>
                       </li>
                     """;
            }
            if (request.getMajor() != null) {
                eventList += """
                    <li>chuyên ngành:
                        <ul>
                    """;
                for (String major : request.getMajor()) {
                    eventList += "<li>" + major + "</li>";
                }
                eventList += """
                            </ul>
                       </li>
                     """;
            }

            eventList += """
                            </ul>
                        """;
            mimeMessageHelper.setText(htmlBody + MailConstant.CONTENT_EVENT_UPDATE_WHEN_APPROVED_MAIL
                    .replace("{userNameChange}", request.getUserNameChange())
                    .replace("{eventName}", request.getEventName())
                    .replace("{event}", eventList)+ MailConstant.FOOTER, true);
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendEmailChangeOrganizer(MailOrganizerSendApproval request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String lecturerList = "";
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());


            for (MailEventOrganizerCustom organizerCustom : request.getLecturerList()) {
                lecturerList += "<li>" + organizerCustom.getUsername() + "</li>";
            }

            mimeMessageHelper.setText(htmlBody+ MailConstant.CONTENT_ORGANIZER_CHANGE_MAIL
                    .replace("{message}", request.getMessage())
                    .replace("{lecturerList}", lecturerList) + MailConstant.FOOTER, true);
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void sendMailAnnounceUpcomingEvent(MailAnnounceUpcomingEvent request) {
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setBcc(request.getMails());

            mimeMessageHelper.setText(htmlBody+ MailConstant.CONTENT_ANNOUNCE_UPCOMING_EVENT
                    .replace("{date}", request.getDate())
                    .replace("{eventCategory}", request.getEventCategory())
                    .replace("{eventName}", request.getEventName())
                    .replace("{lecturerList}", request.getLecturerList())
                    .replace("{locations}", request.getLocations())
                    .replace("{image}", request.getImage())
                    .replace("{resources}", request.getResources()) + MailConstant.FOOTER, true);
            mimeMessageHelper.setSubject(request.getSubject());
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
