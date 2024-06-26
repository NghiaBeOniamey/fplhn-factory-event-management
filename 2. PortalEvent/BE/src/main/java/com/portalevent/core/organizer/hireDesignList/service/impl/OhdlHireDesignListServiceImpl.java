package com.portalevent.core.organizer.hireDesignList.service.impl;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlCreateAndUpdateRequest;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlSearchEventRequest;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlSendImagesEmailRequestOrganizer;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlUpdateImageRequest;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlCustomEventResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventLocationResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventMajorResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlImageEventResponse;
import com.portalevent.core.organizer.hireDesignList.repository.OhdlEventLocationRepository;
import com.portalevent.core.organizer.hireDesignList.repository.OhdlEventMajorRepository;
import com.portalevent.core.organizer.hireDesignList.repository.OhdlEventRepository;
import com.portalevent.core.organizer.hireDesignList.service.OhdlHireDesignListService;
import com.portalevent.entity.Event;
import com.portalevent.entity.EventLocation;
import com.portalevent.infrastructure.apiconstant.ActorConstants;
import com.portalevent.infrastructure.constant.Formality;
import com.portalevent.infrastructure.constant.MailConstant;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.CallApiIdentity;
import com.portalevent.util.CloudinaryUtils;
import com.portalevent.util.CompareUtils;
import com.portalevent.util.LoggerUtil;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Service
@Validated
public class OhdlHireDesignListServiceImpl implements OhdlHireDesignListService {

    private final OhdlEventRepository repository;

    private final OhdlEventMajorRepository ohdlEventMajorRepository;

    private final OhdlEventLocationRepository ohdlEventLocationRepository;

    private final CloudinaryUtils cloudinaryUtils;

    private final OhdlEventLocationRepository eventLocationRepository;

    private final CallApiIdentity callApiIdentity;

    private final JavaMailSender javaMailSender;

    private final LoggerUtil loggerUtil;

    private final PortalEventsSession session;

    @Value("${spring.mail.username}")
    private String sender;

    public OhdlHireDesignListServiceImpl(OhdlEventRepository repository, OhdlEventMajorRepository ohdlEventMajorRepository, OhdlEventLocationRepository ohdlEventLocationRepository, CloudinaryUtils cloudinaryUtils, OhdlEventLocationRepository eventLocationRepository, CallApiIdentity callApiIdentity, JavaMailSender javaMailSender, LoggerUtil loggerUtil, PortalEventsSession session) {
        this.repository = repository;
        this.ohdlEventMajorRepository = ohdlEventMajorRepository;
        this.ohdlEventLocationRepository = ohdlEventLocationRepository;
        this.cloudinaryUtils = cloudinaryUtils;
        this.eventLocationRepository = eventLocationRepository;
        this.callApiIdentity = callApiIdentity;
        this.javaMailSender = javaMailSender;
        this.loggerUtil = loggerUtil;
        this.session = session;
    }


    /**
     * @param request truyền vào các thuộc tính cần search
     * @return nếu param reques có dữ liệu thì thực hiện search, không thì get all data
     */
    @Override
    public PageableObject<OhdlCustomEventResponse> getAllHireDesignList(OhdlSearchEventRequest request) {
        //Phân trang
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize());
        //lấy ra data  của sự kiện cần booking
        Page<OhdlEventResponse> page = repository.getAllHireDesignList(pageable, request,session.getData());
        //Khởi tạo list phân trang mới để custom
        PageableObject<OhdlCustomEventResponse> pageCustom = new PageableObject<>();
        //Call API của người tổ chức
        //Lưu list custom từ list ban đầu
        List<OhdlCustomEventResponse> listCustom1 = new ArrayList<>();
        // vòng for lấy data từ page.getContent()
        for (OhdlEventResponse response : page.getContent()) {
            //Tạo ra đối tượng mới để hứng các thuộc tính từ đối tượng ban đầu
            OhdlCustomEventResponse ohdlCustomEventResponse = new OhdlCustomEventResponse();

            //Lấy data từ 1 phần tử trong sự kiện cần booking
            OhdlEventResponse getOneByIdHireDesign = repository.getOneByIdHireDesign(response.getId(),session.getData());

            //Thực hiện cắt chuỗi của hàm trên
            String[] arrayHireDesign = getOneByIdHireDesign.getOrganizer().split(",");

            //Khởi tạo chức năng nối chuỗi
            StringJoiner organizerIds = new StringJoiner(", ");

            // Lấy ra những người tổ chức của hàm cắt chuỗi trên
            for (String f : arrayHireDesign) {
                //Lấy ra những người tổ chức
//                for (SimpleResponse simpleResponse1 : simpleResponses) {
//                    //ghép chuỗi nếu có người tổ chức hàm trên == data từ API người tổ chức
//                    if (simpleResponse1.getUserCode().equals(f)) {
//                        organizerIds.add(simpleResponse1.getUserName());
//                    }
//                }
            }
            //Lưu data vào đối tượng mới
            ohdlCustomEventResponse.setUserNameOrganizer(organizerIds.toString());
            ohdlCustomEventResponse.setSemester(response.getSemester());
            ohdlCustomEventResponse.setNameLocation(response.getLocation());
            ohdlCustomEventResponse.setId(response.getId());
            ohdlCustomEventResponse.setName(response.getName());
            ohdlCustomEventResponse.setIsHireDesignBackground(response.getIsHireDesignBackground());
            ohdlCustomEventResponse.setIsHireDesignStandee(response.getIsHireDesignStandee());
            ohdlCustomEventResponse.setIsHireDesignBanner(response.getIsHireDesignBanner());
            //Lấy data ảnh và ghép vào thành mảng
            List<String> photosRequire = new ArrayList<>();
            if (ohdlCustomEventResponse.getIsHireDesignBackground()) {
                photosRequire.add("Background");
            }
            if (ohdlCustomEventResponse.getIsHireDesignBanner()) {
                photosRequire.add("Banner");
            }
            if (ohdlCustomEventResponse.getIsHireDesignStandee()) {
                photosRequire.add("Standee");
            }
            ohdlCustomEventResponse.setIsHireLocation(response.getIsHireLocation());
            ohdlCustomEventResponse.setStartTime(response.getStartTime());
            ohdlCustomEventResponse.setEndTime(response.getEndTime());
            ohdlCustomEventResponse.setEventType(response.getEventType());
            ohdlCustomEventResponse.setFormality(response.getFormality());
            ohdlCustomEventResponse.setNameCategory(response.getCategory());
            ohdlCustomEventResponse.setNameMajor(response.getMajor());
            ohdlCustomEventResponse.setNameObject(response.getObject());
            ohdlCustomEventResponse.setTypeBookings(photosRequire);
            listCustom1.add(ohdlCustomEventResponse);
        }

        // add vào list đã khởi tạo
        pageCustom.setCurrentPage(pageCustom.getCurrentPage());
        pageCustom.setData(listCustom1);
        pageCustom.setTotalPages(pageCustom.getTotalPages());

        return pageCustom;
    }

    /**
     * @param id lấy id của sự kiện cần booking
     * @return trả ra dữ liệu tương ứng với id
     */
    @Override
    public OhdlEventResponse getOneHireDesign(String id) {
        return repository.getOneByIdHireDesign(id,session.getData());

    }

    /**
     * @return hàm trả ra list người tổ chức
     */
    @Override
    public List<SimpleResponse> listOrganizerOfHireDesignList() {
//        List<SimpleResponse> listSimple = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_GV);
        return null;
    }

    /**
     * @return hàm trả ra bộ môn
     */
    @Override
    public List<OhdlEventMajorResponse> listMajorOfHireDesignList() {
        return ohdlEventMajorRepository.getListMajorOfHireDesign();
    }

    /**
     * @return hàm trả ra hình thức tổ chức
     */
    @Override
    public List<OhdlEventLocationResponse> listFormalityOfHireDesign() {
        return ohdlEventLocationRepository.getListFormalityOfHireDesign( session.getData());
    }

    /**
     * @return hàm trả ra địa điểm
     */
    @Override
    public List<OhdlEventLocationResponse> listLocationOfHireDesign(String id) {
        return repository.getLocationOfHireDesign(id,session.getData());
    }

    /**
     * @param file    đường dẫn ảnh
     * @param type    kiểu ảnh là 0 == background, 1 == Banner, 2 == Standee
     * @param idEvent id của sự kiện cần booking
     * @param request các loại ảnh cần booking
     * @return
     */
    @Override
    public Event uploadImage(MultipartFile file, int type, String idEvent, OhdlUpdateImageRequest request) {
        Optional<Event> eventOptional = repository.findById(idEvent);
        if (eventOptional.isEmpty()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }

        Event event = eventOptional.get();
        String responseUrl = cloudinaryUtils.uploadImage(file, idEvent, type);
        StringBuffer message = new StringBuffer();
        message.append("Đã sửa thành công ");
        Integer updateImage = 0;

        if (type == 0 && (event.getBackgroundPath() == null || !event.getBackgroundPath().equals(responseUrl))) {
            message.append(CompareUtils.getMessageProperyChange("ảnh Background", event.getBackgroundPath(), responseUrl, "chưa có ảnh Background"));
            event.setBackgroundPath(responseUrl != null ? responseUrl.trim() : null);
            updateImage++;
        }
        if (type == 1 && (event.getBannerPath() == null || !event.getBannerPath().equals(responseUrl))) {

            message.append(CompareUtils.getMessageProperyChange("ảnh Banner", event.getBannerPath(), responseUrl, "chưa có ảnh Banner"));
            event.setBannerPath(responseUrl != null ? responseUrl.trim() : null);
            updateImage++;
        }
        if (type == 2 && (event.getStandeePath() == null || !event.getStandeePath().equals(responseUrl))) {
            message.append(CompareUtils.getMessageProperyChange("ảnh Standee", event.getStandeePath(), responseUrl, "chưa có ảnh Standee"));
            event.setStandeePath(responseUrl != null ? responseUrl.trim() : null);
            updateImage++;
        }

        if (updateImage > 0) {
            loggerUtil.sendLog(message.toString(), idEvent);
            return repository.save(event);
        } else {
            throw new RestApiException(Message.NOTHING_TO_SAVE);
        }
    }

    /**
     * @param id lấy ra id của sự kiện cần booking
     * @return trả ra ảnh cần booking tương ứng theo id của sự kiện cần booking
     */
    @Override
    public OhdlImageEventResponse getImageById(String id) {
        return repository.getImageOfEvent(id,session.getData());
    }

    /**
     * Hàm xóa ảnh nếu cần
     *
     * @param id id lấy ra id của sự kiện cần booking
     * @return trả ra kết quả ảnh cần booking tương ứng theo id của sự kiện cần booking khi xóa
     */
    @Override
    public boolean delete(String id) {
        //Giả sử xóa background từ hàm trên
        // Từ id lấy từ controller lấy ra URL và truyền vào hàm cloudinaryUtils.deleteImage
        // Nhớ check tồn tại
        return cloudinaryUtils.deleteImage(0, id);
    }

    /**
     * @param request hứng data từ input bên FE
     * @return Thêm địa điểm
     */
    @Override
    public EventLocation createLocation(@Valid OhdlCreateAndUpdateRequest request) {
        String regexTrim = "\\s+";
        request.setName(request.getName().replaceAll(regexTrim, " "));
        List<OhdlEventLocationResponse> listNameLocation = eventLocationRepository.findNameLocation(request.getPath(), request.getIdEvent() );
        EventLocation eventLocation = new EventLocation();
        if (!listNameLocation.isEmpty()) {
            throw new RestApiException(Message.EVENT_LOCATION_ALREADY_EXIT);
        }
        eventLocation.setEventId(request.getIdEvent());
        eventLocation.setName(request.getName() != null ? request.getName().trim() : null);
        eventLocation.setPath(request.getPath() != null ? request.getPath().trim() : null);
        eventLocation.setFormality(request.getFormality());
        StringBuffer message = new StringBuffer();
        message.append("Bạn đã tạo địa điểm với id: ");
        message.append(request.getIdEvent());
        message.append("; tên địa điểm: ");
        message.append(request.getName().trim());
        message.append("; địa điểm cụ thể: ");
        message.append(request.getPath().trim());
        message.append("; hình thức tổ chức: ");
        message.append(request.getFormality());
        message.append(".");
        loggerUtil.sendLog(message.toString(), request.getIdEvent());
        EventLocation createdLocation = eventLocationRepository.save(eventLocation);

        //Gửi mail
        OhdlImageEventResponse response = repository.getAllImageById(request.getIdEvent() );
        List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_GV, request.getIdEvent());
        //Lấy data từ 1 phần tử trong sự kiện cần booking
        OhdlEventResponse getOneByIdHireDesign = repository.getOneByIdHireDesign(request.getIdEvent(),session.getData());

        //Thực hiện cắt chuỗi của hàm trên
        String[] arrayHireDesign = getOneByIdHireDesign.getOrganizer().split(",");

        //Khởi tạo chức năng nối chuỗi
        StringJoiner organizerIds = new StringJoiner(", ");

        for (String f : arrayHireDesign) {
            //Lấy ra những người tổ chức
            for (SimpleResponse simpleResponse1 : simpleResponses) {
                //ghép chuỗi nếu có người tổ chức hàm trên == data từ API người tổ chức
                if (simpleResponse1.getUserCode().equals(f)) {
                    organizerIds.add(simpleResponse1.getEmailFE());
                }
            }
        }
        List<OhdlEventLocationResponse> findEventLocation = eventLocationRepository.findLocationById(request.getIdEvent() );

        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String locationList = "";
        String typeFormality = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(organizerIds.toString().split(", ")[0]);
            String[] ccRecipients = organizerIds.toString().split(", ");
            if (ccRecipients.length > 1) {
                mimeMessageHelper.setCc(Arrays.copyOfRange(ccRecipients, 1, ccRecipients.length));
            }
            mimeMessageHelper.setSubject("[PTPM-XLDL-UDPM] Thư thông báo chỉnh sửa địa điểm sự kiện cần booking");
            for (OhdlEventLocationResponse location : findEventLocation) {
                if (location.getFormality() == 1) {
                    locationList += "<li> Offline - " + location.getName() + ": " + location.getPath() + "</li>";
                } else {
                    locationList += "<li> Online - " + location.getName() + ": <a href='" + location.getPath() + "'>Tại đây</a></li>";
                }
            }
            if (findEventLocation.isEmpty()) {
                locationList += "Chưa có địa điểm";
            }
            if (request.getFormality() == Formality.ONLINE) {
                typeFormality += "Online";
            } else {
                typeFormality += "Offline";
            }
            mimeMessageHelper.setText(htmlBody + MailConstant.CONTENT_ADD_LOCATION_MAIL
                    .replace("{eventName}", response.getName())
                    .replace("{startDate}", dateFormat.format(new Date(response.getStartDate())))
                    .replace("{endDate}", dateFormat.format(new Date(response.getEndDate())))
                    .replace("{newLocation}", request.getName())
                    .replace("{pathLocation}", request.getPath())
                    .replace("{formalityLocation}", typeFormality)
                    .replace("{locations}", locationList) + MailConstant.FOOTER, true);
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return createdLocation;

    }

    /**
     * @param id      lấy id của sự kiện cần booking
     * @param request lấy data từ Input FE
     * @return Cập nhật địa điểm
     */
    @Override
    public EventLocation updateLocation(String id, @Valid OhdlCreateAndUpdateRequest request) {
        Optional<EventLocation> eventLocation = eventLocationRepository.findById(id);
        if (eventLocation.isEmpty()) {
            throw new RestApiException(Message.EVENT_LOCATION_NOT_EXIT);
        }
        StringBuffer message = new StringBuffer();
        message.append("Đã cập nhật ");
        Integer updateLocation = 0;
        if (!eventLocation.get().getName().equals(request.getName())) {
            message.append(CompareUtils.getMessageProperyChange("tên địa điểm", eventLocation.get().getName(), request.getName(), "chưa có tên địa điểm"));
            updateLocation++;
        }
        if (!eventLocation.get().getPath().equals(request.getPath())) {
            message.append(CompareUtils.getMessageProperyChange("chi tiết địa điểm", eventLocation.get().getPath(), request.getPath(), "chưa có chi tiết địa điểm"));
            updateLocation++;
        }
        if (!eventLocation.get().getFormality().equals(request.getFormality())) {
            message.append(CompareUtils.getMessageProperyChange("hình thức tổ chức", eventLocation.get().getFormality(), request.getFormality(), "chưa có hình thức tổ chức"));
            updateLocation++;
        }
        eventLocation.get().setName(request.getName() != null ? request.getName().trim() : null);
        eventLocation.get().setPath(request.getPath() != null ? request.getPath().trim() : null);
        eventLocation.get().setFormality(request.getFormality());

        OhdlImageEventResponse response = repository.getAllImageById(request.getIdEvent() );
        List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_GV, eventLocation.get().getEventId());
        //Lấy data từ 1 phần tử trong sự kiện cần booking
        OhdlEventResponse getOneByIdHireDesign = repository.getOneByIdHireDesign(request.getIdEvent(),session.getData());

        //Thực hiện cắt chuỗi của hàm trên
        String[] arrayHireDesign = getOneByIdHireDesign.getOrganizer().split(",");

        //Khởi tạo chức năng nối chuỗi
        StringJoiner organizerIds = new StringJoiner(", ");

        for (String f : arrayHireDesign) {
            //Lấy ra những người tổ chức
            for (SimpleResponse simpleResponse1 : simpleResponses) {
                //ghép chuỗi nếu có người tổ chức hàm trên == data từ API người tổ chức
                if (simpleResponse1.getUserCode().equals(f)) {
                    organizerIds.add(simpleResponse1.getEmailFE());
                }
            }
        }
        List<OhdlEventLocationResponse> findEventLocation = eventLocationRepository.findLocationById(request.getIdEvent() );

        OhdlEventLocationResponse findOneLocation = eventLocationRepository.findOneLocationById(id );

        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        String locationList = "";
        String typeFormality = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(organizerIds.toString().split(", ")[0]);
            String[] ccRecipients = organizerIds.toString().split(", ");
            if (ccRecipients.length > 1) {
                mimeMessageHelper.setCc(Arrays.copyOfRange(ccRecipients, 1, ccRecipients.length));
            }
            mimeMessageHelper.setSubject("[PTPM-XLDL-UDPM] Thư thông báo chỉnh sửa địa điểm sự kiện cần booking");
            for (OhdlEventLocationResponse location : findEventLocation) {
                if (location.getFormality() == 1) {
                    locationList += "<li> Offline - " + location.getName() + ": " + location.getPath() + "</li>";
                } else {
                    locationList += "<li> Online - " + location.getName() + ": <a href='" + location.getPath() + "'>Tại đây</a></li>";
                }
            }
            if (findEventLocation.isEmpty()) {
                locationList += "Chưa có địa điểm";
            }
            if (findOneLocation.getFormality() == 0) {
                typeFormality += "Online";
            } else {
                typeFormality += "Offline";
            }
            mimeMessageHelper.setText(htmlBody + MailConstant.CONTENT_UPDATE_LOCATION_MAIL
                    .replace("{eventName}", response.getName())
                    .replace("{startDate}", dateFormat.format(new Date(response.getStartDate())))
                    .replace("{endDate}", dateFormat.format(new Date(response.getEndDate())))
                    .replace("{newLocation}", request.getName())
                    .replace("{pathLocation}", request.getPath())
                    .replace("{formalityLocation}", typeFormality)
                    .replace("{oldLocation}", findOneLocation.getName())
                    .replace("{oldpathLocation}", findOneLocation.getPath())
                    .replace("{oldformalityLocation}", typeFormality)
                    .replace("{locations}", locationList) + MailConstant.FOOTER, true);
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (updateLocation > 0) {
            loggerUtil.sendLog(message.toString(), request.getIdEvent());
            return eventLocationRepository.save(eventLocation.get());
        } else {
            throw new RestApiException(Message.NOTHING_TO_SAVE);
        }
    }

    /**
     * @param id lấy id của sự kiện cần booking
     * @return xóa địa điểm
     */
    @Override
    public Boolean deleteLocation(String id, String idEvent) {
        OhdlEventLocationResponse findOneLocation = eventLocationRepository.findOneLocationById(id );
        Optional<EventLocation> eventLocation = eventLocationRepository.findById(id);
        if (eventLocation.isEmpty()) {
            throw new RestApiException(Message.EVENT_LOCATION_NOT_EXIT);
        }
        if (eventLocation != null) {
            eventLocationRepository.deleteById(eventLocation.get().getId());
            StringBuffer message = new StringBuffer();
            message.append("Đã cập nhật hình thức của sự kiện từ ");
            message.append(eventLocation.get().getName().trim());
            message.append(" - ");
            message.append(eventLocation.get().getPath());
            loggerUtil.sendLog(message.toString(), idEvent);
            OhdlImageEventResponse response = repository.getAllImageById(idEvent );
            List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_GV, idEvent);
            //Lấy data từ 1 phần tử trong sự kiện cần booking
            OhdlEventResponse getOneByIdHireDesign = repository.getOneByIdHireDesign(idEvent,session.getData());

            //Thực hiện cắt chuỗi của hàm trên
            String[] arrayHireDesign = getOneByIdHireDesign.getOrganizer().split(",");

            //Khởi tạo chức năng nối chuỗi
            StringJoiner organizerIds = new StringJoiner(", ");

            for (String f : arrayHireDesign) {
                //Lấy ra những người tổ chức
                for (SimpleResponse simpleResponse1 : simpleResponses) {
                    //ghép chuỗi nếu có người tổ chức hàm trên == data từ API người tổ chức
                    if (simpleResponse1.getUserCode().equals(f)) {
                        organizerIds.add(simpleResponse1.getEmailFE());
                    }
                }
            }
            List<OhdlEventLocationResponse> findEventLocation = eventLocationRepository.findLocationById(idEvent );
            String htmlBody = MailConstant.HEADER.replace("{title}", "");
            String locationList = "";
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
                ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
                mimeMessageHelper.setFrom(sender);
                mimeMessageHelper.setTo(organizerIds.toString().split(", ")[0]);
                String[] ccRecipients = organizerIds.toString().split(", ");
                if (ccRecipients.length > 1) {
                    mimeMessageHelper.setCc(Arrays.copyOfRange(ccRecipients, 1, ccRecipients.length));
                }
                mimeMessageHelper.setSubject("[PTPM-XLDL-UDPM] Thư thông báo chỉnh sửa địa điểm sự kiện cần booking");
                for (OhdlEventLocationResponse location : findEventLocation) {
                    if (location.getFormality() == 1) {
                        locationList += "<li> Offline - " + location.getName() + ": " + location.getPath() + "</li>";
                    } else {
                        locationList += "<li> Online - " + location.getName() + ": <a href='" + location.getPath() + "'>Tại đây</a></li>";
                    }
                }
                if (findEventLocation.isEmpty()) {
                    locationList += "Chưa có địa điểm";
                }
                mimeMessageHelper.setText(htmlBody + MailConstant.CONTENT_DELETE_LOCATION_MAIL
                        .replace("{eventName}", response.getName())
                        .replace("{startDate}", dateFormat.format(new Date(response.getStartDate())))
                        .replace("{endDate}", dateFormat.format(new Date(response.getEndDate())))
                        .replace("{oldLocation}", findOneLocation.getName())
                        .replace("{oldpathLocation}", findOneLocation.getPath())
                        .replace("{oldformalityLocation}", findOneLocation.getFormality() == 0 ? "Online" : " Ofline")
                        .replace("{locations}", locationList) + MailConstant.FOOTER, true);
                mimeMessageHelper.addInline("logoImage", resource);
                javaMailSender.send(mimeMessage);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    @Async
    public void sendEmailImageEventHireDesign(OhdlSendImagesEmailRequestOrganizer request) {
        String regexTrim = "\\s+";
        request.setEventName(request.getEventName().replaceAll(regexTrim, " "));
        OhdlImageEventResponse response = repository.getAllImageById(request.getId() );
//        List<SimpleResponse> simpleResponses = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_GV, id);
        //Lấy data từ 1 phần tử trong sự kiện cần booking
        OhdlEventResponse getOneByIdHireDesign = repository.getOneByIdHireDesign(request.getId(),session.getData());

        //Thực hiện cắt chuỗi của hàm trên
        String[] arrayHireDesign = getOneByIdHireDesign.getOrganizer().split(",");

        //Khởi tạo chức năng nối chuỗi
        StringJoiner organizerIds = new StringJoiner(", ");

        for (String f : arrayHireDesign) {
            //Lấy ra những người tổ chức
//            for (SimpleResponse simpleResponse1 : simpleResponses) {
//                //ghép chuỗi nếu có người tổ chức hàm trên == data từ API người tổ chức
//                if (simpleResponse1.getUserCode().equals(f)) {
//                    organizerIds.add(simpleResponse1.getEmailFE());
//                }
//            }
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        String htmlBody = MailConstant.HEADER.replace("{title}", "");
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.toString());
            ClassPathResource resource = new ClassPathResource(MailConstant.LOGO_PATH);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(organizerIds.toString().split(", ")[0]);
            String[] ccRecipients = organizerIds.toString().split(", ");
            if (ccRecipients.length > 1) {
                mimeMessageHelper.setCc(Arrays.copyOfRange(ccRecipients, 1, ccRecipients.length));
            }
            mimeMessageHelper.setSubject("[PTPM-XLDL-UDPM] Thư thông báo chỉnh sửa ảnh sự kiện cần booking");
            String linkBackground = response.getBackgroundPath() == null ? "Link Background: Chưa có ảnh" : "Link Background:  <a href='" + response.getBackgroundPath() + "'>Tại đây</a>";
            String linkStandee = response.getStandeePath() == null ? "Link Banner: Chưa có ảnh" : "Link Banner: <a href='" + response.getStandeePath() + "'>Tại đây</a>";
            String linkBanner = response.getBannerPath() == null ? "Link Standee: Chưa có ảnh" : "Link Standee: <a href='" + response.getBannerPath() + "'>Tại đây</a>";

            mimeMessageHelper.setText(htmlBody + MailConstant.CONTENT_UPDATE_IMAGES_MAIL
                    .replace("{eventName}", response.getName())
                    .replace("{startDate}", dateFormat.format(new Date(response.getStartDate())))
                    .replace("{endDate}", dateFormat.format(new Date(response.getEndDate())))
                    .replace("{linkBackground}", linkBackground)
                    .replace("{linkStandee}", linkStandee)
                    .replace("{linkBanner}", linkBanner) + MailConstant.FOOTER, true);
            mimeMessageHelper.addInline("logoImage", resource);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
