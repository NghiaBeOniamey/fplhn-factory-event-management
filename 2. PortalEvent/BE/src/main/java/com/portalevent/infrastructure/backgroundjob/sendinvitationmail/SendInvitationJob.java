package com.portalevent.infrastructure.backgroundjob.sendinvitationmail;

import com.portalevent.core.common.SimpleResponse;
import com.portalevent.entity.InvitationTime;
import com.portalevent.infrastructure.backgroundjob.sendinvitationmail.model.EventDTO;
import com.portalevent.infrastructure.constant.MailConstant;
import com.portalevent.repository.EventRepository;
import com.portalevent.repository.InvitationTimeRepository;
import com.portalevent.repository.ParticipantRepository;
import jakarta.mail.internet.MimeMessage;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author SonPT
 */
public class SendInvitationJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // Lấy data được truyền từ Service khi tạo job
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String eventId = (String) dataMap.get("eventId");
        InvitationTime invitationTime = (InvitationTime) dataMap.get("invitationTime");
        ParticipantRepository participantRepository = (ParticipantRepository) dataMap.get("participantRepository");
        EventRepository eventRepository = (EventRepository) dataMap.get("eventRepository");
        InvitationTimeRepository invitationTimeRepository = (InvitationTimeRepository) dataMap.get("invitationTimeRepository");
        List<SimpleResponse> listOganizer = (List<SimpleResponse>) dataMap.get("listOganizer");
        //Lấy Danh sách email người tham gia
        List<String> lstEmailParticipant = participantRepository.getAllEmailByEventId(eventId);
        if (lstEmailParticipant.isEmpty()) {
            return;
        }
        // Đẩy danh sách tất cả các người tổ chức được truyền từ Service vào map
        Map<String, SimpleResponse> mapAllOrganizer = new HashMap<>();
        for (SimpleResponse item :listOganizer) {
            mapAllOrganizer.put(item.getUserCode(), item);
        }

        //Config mail
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setPort(587);
        javaMailSender.setHost("smtp.gmail.com");
        javaMailSender.setUsername("event.udpm.portal@gmail.com");
        javaMailSender.setPassword("tcixsvjaookltfce");
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        javaMailSender.setJavaMailProperties(properties);

        String body = MailConstant.HEADER;
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");

        // Lấy thông tin của sự kiện từ repository
        List<EventDTO> event = eventRepository.getEventByIdForSendMail(eventId);
        if (event.isEmpty()) {
            return;
        }
        List<String> lstOrganizerId = new ArrayList<>();
        Map<String, String> mapResource = new HashMap<>();
        Map<String, String> mapLocation = new HashMap<>();
        for (EventDTO item :event) {
            // Lọc ra list id người tổ chức
            if (item.getOrganizerId() != null) {
                if (!lstOrganizerId.contains(item.getOrganizerId())) {
                    lstOrganizerId.add(item.getOrganizerId());
                }
            }
            // Lọc ra list Resource
            if (item.getResourceId() != null) {
                if (!mapResource.containsKey(item.getResourceId())) {
                   mapResource.put(item.getResourceId(), "<li>" + item.getResourceName() + ": " + "<a href=\"" + item.getResourcePath() + "\">Tại đây</a></li>");
                }
            }
            //Lọc ra list location
            if (item.getLocationId() != null) {
                if (!mapLocation.containsKey(item.getLocationId())) {
                    if (item.getFormality() == 0) {
						mapLocation.put(item.getLocationId(), "<li>ONLINE - " + item.getLocationName() + ": " + "<a href=\"" + item.getLocationPath() + "\" >Tại đây</a></li>");
                    } else {
                        mapLocation.put(item.getLocationId(), "<li>OFFLINE - " + item.getLocationName() + ": " + item.getLocationPath());
                    }
                }
            }
        }
        StringBuilder organizer = new StringBuilder("");
        StringBuilder time = new StringBuilder("");
        StringBuilder eventResource = new StringBuilder();
        StringBuilder eventLocation = new StringBuilder("");
        StringBuilder eventImage = new StringBuilder("");

        //Lấy ra thông tin người tổ chức từ map tất cả người tổ chức
        for (String item :lstOrganizerId) {
            organizer.append("<li>").append(mapAllOrganizer.get(item).getUserName()).append(" - ").append(mapAllOrganizer.get(item).getName()).append("</li>");
        }

        Date startTime = new Date(event.get(0).getStartTime());
        Date endTime = new Date(event.get(0).getEndTime());
        if (new Date(sdfDate.format(startTime)).compareTo(new Date(sdfDate.format(endTime))) == 0) {
            //Nếu thời gian diễn ra nằm trong cùng 1 ngày
            time.append("<li>Thời gian: ").append(sdfTime.format(startTime)).append(" - ").append(sdfTime.format(endTime)).append("</li><li>Ngày: ").append(sdfDate.format(startTime)).append("</li>");
        } else {
            time.append("<li>Thời gian: ").append(sdfDateTime.format(startTime)).append(" - ").append(sdfDateTime.format(endTime)).append("</li>");
        }
        // Tạo HTML cho các địa điểm đã được lọc
        if (!mapLocation.isEmpty()) {
            for (String item :mapLocation.values()) {
                eventLocation.append(item);
            }
        }
        //Kiểm tra và tạo HTMl cho background và banner của sự kiện nếu có
        if (event.get(0).getBackground() != null) {
            eventImage.append("<li>Link Background: <a href=\"").append(event.get(0).getBackground()).append("\">Tại đây</a></li>");
        }
        if (event.get(0).getBanner() != null) {
            eventImage.append("<li>Link Banner: <a href=\"").append(event.get(0).getBanner()).append("\">Tại đây</a></li>");
        }
        // Tạo HTML cho các Resource đã lọc
        if (!mapResource.isEmpty()) {
            eventResource.append("<li>Tài nguyên: <ul>");
            for (String item :mapResource.values()) {
                eventResource.append(item);
            }
            eventResource.append("</ul></li>");
        }

        //** Title
        body = body.replace("{title}",  "Thư mời tham dự");
        //** Body
        body += MailConstant.CONTENT_EVENT_COMING_SOON;
        body = body.replace("{eventCategory}", event.get(0).getCategoryName());
        body = body.replace("{eventName}", event.get(0).getEventName());
        body = body.replace("{organizer}", organizer);
        body = body.replace("{contentList}", event.get(0).getDescription());
        body = body.replace("{time}", time);
        body = body.replace("{locations}", eventLocation);
        body = body.replace("{image}", eventImage);
        body = body.replace("{resources}", eventResource);
        body += MailConstant.FOOTER;

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom("bit.poly.hn@gmail.com");
            mimeMessageHelper.setTo(lstEmailParticipant.get(0));
            String[] emailBcc = lstEmailParticipant.toArray(new String[0]);
            mimeMessageHelper.setBcc(emailBcc);
            mimeMessageHelper.setText(body, true);
            mimeMessageHelper.setSubject(MailConstant.SUBJECT + "- Thư tham dự " + event.get(0).getCategoryName() + " - " + event.get(0).getEventName());
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
            invitationTime.setStatus(true);
            invitationTimeRepository.save(invitationTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
