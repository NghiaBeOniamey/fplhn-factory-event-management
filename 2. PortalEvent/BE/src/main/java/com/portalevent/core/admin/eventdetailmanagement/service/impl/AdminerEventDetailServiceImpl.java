package com.portalevent.core.admin.eventdetailmanagement.service.impl;

import com.portalevent.core.admin.eventdetailmanagement.model.request.AdminerDeleteCommentRequest;
import com.portalevent.core.admin.eventdetailmanagement.model.request.AdminerPostCommentRequest;
import com.portalevent.core.admin.eventdetailmanagement.model.request.AdminerReplyCommentRequest;
import com.portalevent.core.admin.eventdetailmanagement.model.response.*;
import com.portalevent.core.admin.eventdetailmanagement.repository.*;
import com.portalevent.core.admin.eventdetailmanagement.service.AdminerEventDetailService;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.model.request.AdminerEventApproveRequest;
import com.portalevent.core.admin.eventwaitingapprovalmanagement.repository.AdminerCommentRepository;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.entity.*;
import com.portalevent.infrastructure.apiconstant.ActorConstants;
import com.portalevent.infrastructure.constant.*;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.repository.PeriodicEventMajorRepository;
import com.portalevent.repository.PeriodicEventObjectRepository;
import com.portalevent.repository.PeriodicEventRepository;
import com.portalevent.util.CallApiIdentity;
import com.portalevent.util.LoggerUtil;
import com.portalevent.util.mail.EmailUtils;
import com.portalevent.util.mail.MailAnnounceUpcomingEvent;
import com.portalevent.util.mail.MailRequest;
import com.portalevent.util.repository.DepartmentApiRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminerEventDetailServiceImpl implements AdminerEventDetailService {

    private final AdminerEventDetailRepository eventRepository;

    private final AdminerCommentRepository commentRepository;

    private final AdminerAgendaItemRepository agendaItemRepository;

    private final AdminerResourceReposiotry resourceReposiotry;

    private final AdminerLocationRepository locationRepository;

    private final AdminerEDObjectRepository objectRepository;

    private final AdminerEDMajorRepository majorRepository;

    private final CallApiIdentity callApiIdentity;

    private final PortalEventsSession session;

    private final AdminerdEvidenceRepository evidenceRepository;

    private final PeriodicEventMajorRepository periodicEventMajorRepository;

    private final PeriodicEventObjectRepository periodicEventObjectRepository;

    private final PeriodicEventRepository periodicEventRepository;

    private final AdminerEventOrganizerRepository eventOrganizerRepository;

    private final LoggerUtil loggerUtil;

    private final EmailUtils emailUtils;

    public AdminerEventDetailServiceImpl(AdminerEDMajorRepository majorRepository, AdminerAgendaItemRepository agendaItemRepository, AdminerEventDetailRepository eventRepository, AdminerCommentRepository commentRepository, @Qualifier(PeriodicEventRepository.NAME) PeriodicEventRepository periodicEventRepository, AdminerResourceReposiotry resourceReposiotry, AdminerLocationRepository locationRepository, AdminerEDObjectRepository objectRepository, CallApiIdentity callApiIdentity, PortalEventsSession session, AdminerdEvidenceRepository evidenceRepository, @Qualifier(PeriodicEventMajorRepository.NAME) PeriodicEventMajorRepository periodicEventMajorRepository, AdminerEventOrganizerRepository eventOrganizerRepository, EmailUtils emailUtils, @Qualifier(PeriodicEventObjectRepository.NAME) PeriodicEventObjectRepository periodicEventObjectRepository, LoggerUtil loggerUtil) {
        this.majorRepository = majorRepository;
        this.agendaItemRepository = agendaItemRepository;
        this.eventRepository = eventRepository;
        this.commentRepository = commentRepository;
        this.periodicEventRepository = periodicEventRepository;
        this.resourceReposiotry = resourceReposiotry;
        this.locationRepository = locationRepository;
        this.objectRepository = objectRepository;
        this.callApiIdentity = callApiIdentity;
        this.session = session;
        this.evidenceRepository = evidenceRepository;
        this.periodicEventMajorRepository = periodicEventMajorRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.emailUtils = emailUtils;
        this.periodicEventObjectRepository = periodicEventObjectRepository;
        this.loggerUtil = loggerUtil;
    }

    /**
     * @param idEvent: Id của sự kiện
     * @return Thông tin chi tiết của sự kiện
     */
    @Transactional
    @Override
    public AdminerEventDetailApprovedCustom getDetailEventApproved(String idEvent) {
        // Get detail event by id event
        AdminerEventDetailApprovedResponse eventDetail = eventRepository.getDetailApprovedById(idEvent).get();
        SimpleResponse responseApprover = null;
        if (!(eventDetail.getApproverId() == null)) {
            // Call API identity get information approver
            responseApprover = callApiIdentity.handleCallApiGetUserById(eventDetail.getApproverId());
        }
        AdminerEventDetailApprovedCustom eventDetailCustom = new AdminerEventDetailApprovedCustom();
        eventDetailCustom.setId(eventDetail.getId());
        eventDetailCustom.setName(eventDetail.getName());
        eventDetailCustom.setStartTime(eventDetail.getStartTime());
        eventDetailCustom.setEndTime(eventDetail.getEndTime());
        eventDetailCustom.setCategoryName(eventDetail.getCategoryName());
        eventDetailCustom.setDescription(eventDetail.getDescription());
        eventDetailCustom.setReason(eventDetail.getReason());
        eventDetailCustom.setExpectedParticipant(eventDetail.getExpectedParticipant());
        eventDetailCustom.setBackground(eventDetail.getBackground());
        eventDetailCustom.setStandee(eventDetail.getStandee());
        eventDetailCustom.setBanner(eventDetail.getBanner());
        eventDetailCustom.setStatus(eventDetail.getStatus());
        eventDetailCustom.setIsHireDesignStandee(eventDetail.getIsHireDesignStandee());
        eventDetailCustom.setIsHireDesignBanner(eventDetail.getIsHireDesignBanner());
        eventDetailCustom.setIsHireDesignBackground(eventDetail.getIsHireDesignBackground());
        eventDetailCustom.setParticipants(eventDetail.getParticipants());
        eventDetailCustom.setIsWaitApprovalPeriodically(eventDetail.getIsWaitApprovalPeriodically());
        eventDetailCustom.setNumberParticipants(eventDetail.getNumberParticipants());
        eventDetailCustom.setAvgRate(eventDetail.getAvgRate());
        eventDetailCustom.setEventType(eventDetail.getEventType() != null ? eventDetail.getEventType() : null);
        eventDetailCustom.setIsNotEnoughTimeApproval(eventDetail.getIsNotEnoughTimeApproval());
        eventDetailCustom.setDepartmentName(eventDetail.getDepartmentName());
        eventDetailCustom.setMajorName(eventDetail.getMajorName());

        if (eventDetail.getStatus() == 3) {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm dd/MM/yyyy");
            List<EventOrganizer> eventOrganizerList = eventOrganizerRepository.findAllByEventId(idEvent);
            eventOrganizerList.forEach(item -> {
                List<AdminerEventOverlapOrganizer> listEventOverlap = eventRepository.getEventOverlapOrganizer(idEvent, eventDetail.getStartTime(), eventDetail.getEndTime());
                if (!listEventOverlap.isEmpty()) {
                    SimpleResponse simpleResponse = callApiIdentity.handleCallApiGetUserById(item.getOrganizerCode());
                    eventDetailCustom.setMessageOverlapOrganizer("Người tổ chức "
                            + simpleResponse.getName()
                            + " trong sự kiện này trùng thời gian tham gia tổ chức với sự kiện "
                            + listEventOverlap.get(0).getName() + " tổ chức vào " + sdf.format(new Date(eventDetail.getStartTime()))
                            + "-"
                            + sdf.format(new Date(eventDetail.getEndTime())));
                }
            });
        }

        if (responseApprover != null) {
            eventDetailCustom.setApproverName(responseApprover.getName());
            eventDetailCustom.setApproverUserName(responseApprover.getUserName());
            eventDetailCustom.setApproverEmail(responseApprover.getEmailFE());
        }
        return eventDetailCustom;
    }

    /**
     * @param eventId:    Id của sự kiện
     * @param pageNumber: Vị trí page
     * @return Trả về danh sách comment theo pageNumber
     * @description Danh sách comment trả về, các commnet sẽ kèm theo reply comment nếu có
     */
    @Override
    public List<AdminerAllCommentResponse> getComment(String eventId, int pageNumber) {
        long a = new Date().getTime();

        if (pageNumber < 0) {
            throw new RestApiException(Message.PAGE_NOT_EXIST);
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Pageable pageable = PageRequest.of(pageNumber, 10);
        List<String> lstUserIdToGet = new ArrayList<>();
        List<AdminerAllCommentResponse> listComment = new ArrayList<>();
        List<AdminerReplyCommentResponse> listReplyComment = new ArrayList<>();
        List<String> listReplyCommentId = new ArrayList<>();
        Page<Comment> commentFromDB = commentRepository.findByEventIdAndReplyIdIsNullOrderByLastModifiedDateDesc(eventId, pageable);
        for (Comment comment : commentFromDB) {
            AdminerAllCommentResponse commentResponse = new AdminerAllCommentResponse();
            commentResponse.setTotalPages(commentFromDB.getTotalPages());
            commentResponse.setCurrentPage(commentFromDB.getNumber());
            commentResponse.setId(comment.getId());
            commentResponse.setLastModifiedDate(dateFormat.format(new Date(comment.getLastModifiedDate())));
            commentResponse.setComment(comment.getComment());
            commentResponse.setUserId(comment.getUserId());
            commentResponse.setIsReply(false);
            commentResponse.setListReply(new ArrayList<>());
            lstUserIdToGet.add(comment.getUserId());
            listReplyCommentId.add(comment.getId());
            listComment.add(commentResponse);
        }

        List<AdminerCommentResponse> listReply = commentRepository.getReplyCommentByReplyIdAndEventId(eventId, listReplyCommentId);
        listReply.forEach(comment -> {
            AdminerReplyCommentResponse replyCommentResponse = new AdminerReplyCommentResponse();
            replyCommentResponse.setId(comment.getId());
            replyCommentResponse.setLastModifiedDate(dateFormat.format(new Date(comment.getLastModifiedDate())));
            replyCommentResponse.setComment(comment.getComment());
            replyCommentResponse.setReplyId(comment.getReplyId());
            replyCommentResponse.setUserId(comment.getUserId());

            lstUserIdToGet.add(comment.getUserId());
            listReplyComment.add(replyCommentResponse);
        });
        listReplyComment.forEach(replyComment -> {
            listComment.forEach(comment -> {
                if (comment.getId().equals(replyComment.getReplyId())) {
                    comment.addElementToListReply(replyComment);
                }
            });
        });

        if (lstUserIdToGet.size() > 0) {
            //Lấy thông tin user từ list phía trên
            List<SimpleResponse> listUser = callApiIdentity.handleCallApiGetListUserByListId(lstUserIdToGet);
            //Map chứa User
            Map<String, SimpleResponse> mapUser = new HashMap<>();
            listUser.forEach(user -> {
                mapUser.put(user.getUserCode(), user);
            });
            listComment.forEach(comment -> {
                //Duyệt qua các phần tử comment và reply comment để gán các thông tin user được lấy từ Map thông qua UserId
                SimpleResponse commentUser = mapUser.get(comment.getUserId());
                if (commentUser != null) {
                    comment.setUserId(commentUser.getUserCode());
                    comment.setEmail(commentUser.getEmailFE());
                    comment.setAvatar(commentUser.getPicture());
                }
                if (comment.getListReply() != null) {
                    for (AdminerReplyCommentResponse replyComment : comment.getListReply()) {
                        SimpleResponse replyCommentUser = mapUser.get(replyComment.getUserId());
                        if (replyCommentUser != null) {
                            replyComment.setUserId(replyCommentUser.getUserCode());
                            replyComment.setEmail(replyCommentUser.getName());
                            replyComment.setAvatar(replyCommentUser.getPicture());
                        }
                    }
                }
            });
        }
        Collections.reverse(listComment);
        return listComment;
    }

    /**
     * @param idEvent: Id của sự kiện
     * @return Các evidence của sự kiện
     */
    @Override
    public List<AdminerEvidenceResponse> getEvidenceByIdEvent(String idEvent) {
        return evidenceRepository.getEvidenceByIdEvent(idEvent);
    }

    /**
     * Phê duyệt sự kiện hàng kỳ
     *
     * @param idEvent
     * @return
     */
    @Override
    public Boolean approverPeriodicEvent(String idEvent) {
        Optional<Event> eventFind = eventRepository.findById(idEvent);
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        if (eventFind.get().getIsWaitApprovalPeriodically() != ApprovalPeriodicallyStatus.DA_GUI_YEU_CAU) {
            throw new RestApiException(Message.INVALID_STATUS);
        }
        eventFind.get().setIsWaitApprovalPeriodically(ApprovalPeriodicallyStatus.DA_PHE_DUYET);
        List<AdminerEventMajorResponse> eventMajorList = eventRepository.getAllEventMajorByIdEvent(eventFind.get().getId());
        List<AdminerEventObjectResponse> eventObjectList = eventRepository.getAllEventObjectByIdEvent(eventFind.get().getId());
        PeriodicEvent periodicEvent = new PeriodicEvent();
        periodicEvent.setName(eventFind.get().getName());
        periodicEvent.setEventType(eventFind.get().getEventType());
        periodicEvent.setDescription(eventFind.get().getDescription());
        periodicEvent.setExpectedParticipants(eventFind.get().getExpectedParticipants());
        periodicEvent.setCategoryId(eventFind.get().getCategoryId());
        periodicEvent.setSubjectCode(eventFind.get().getSubjectCode());
        periodicEvent.setTrainingFacilityCode(eventFind.get().getTrainingFacilityCode());
        PeriodicEvent periodicEventNew = periodicEventRepository.save(periodicEvent);
        List<PeriodicEventMajor> listMajorNew = new ArrayList<>();
        eventMajorList.forEach(major -> {
            PeriodicEventMajor periodicEventMajor = new PeriodicEventMajor();
            periodicEventMajor.setPeriodicEventId(periodicEventNew.getId());
            periodicEventMajor.setMajorId(major.getMajorId());
            listMajorNew.add(periodicEventMajor);
        });
        periodicEventMajorRepository.saveAll(listMajorNew);

        List<PeriodicEventObject> listObjectNew = new ArrayList<>();
        eventObjectList.forEach(object -> {
            PeriodicEventObject periodicEventObject = new PeriodicEventObject();
            periodicEventObject.setPeriodicEventId(periodicEventNew.getId());
            periodicEventObject.setObjectId(object.getObjectId());
            listObjectNew.add(periodicEventObject);
        });
        periodicEventObjectRepository.saveAll(listObjectNew);
        eventRepository.save(eventFind.get());
        loggerUtil.sendLog("Phê duyệt thành công sự kiện " + eventFind.get().getName() + " thành sự kiện hàng kỳ", eventFind.get().getId());

        MailRequest request = new MailRequest();
        StringBuilder time = new StringBuilder("");
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
        Date startTime = new Date(eventFind.get().getStartTime());
        Date endTime = new Date(eventFind.get().getEndTime());
        if (new Date(sdfDate.format(startTime)).compareTo(new Date(sdfDate.format(endTime))) == 0) {
            time.append("<li>Thời gian diễn ra: ").append(sdfTime.format(startTime)).append(" - ").append(sdfTime.format(endTime)).append("</li><li>Ngày: ").append(sdfDate.format(startTime)).append("</li>");
        } else {
            time.append("<li>Thời gian diễn ra: ").append(sdfDateTime.format(startTime)).append(" - ").append(sdfDateTime.format(endTime)).append("</li>");
        }
        request.setSubject("- Thư thông báo phê duyệt sự kiện hàng kỳ " + eventFind.get().getName());
        request.setTitle("");
        String htmlBody = MailConstant.CONTENT_EVENT_APPROVE.replace("{nameEvent}", eventFind.get().getName())
                .replace("{timeEvent}", time.toString())
                .replace("{nameApprove}", session.getCurrentName());
        request.setBody(htmlBody);
        // Lấy ds người tổ chức của sự kiện
        List<String> listIdOrganizer = eventOrganizerRepository.getIdOrganizerByIdEvent(eventFind.get().getId());
        List<SimpleResponse> listAllOrganizer = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer);

        List<String> emailsWithId = listAllOrganizer.stream()
                .map(SimpleResponse::getEmailFE)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());

        request.setMails(emailsWithId.toArray(new String[0]));
        emailUtils.sendEmail(request);
        return true;
    }

    @Override
    public Boolean noApproverPeriodicEvent(String idEvent, String reason) {
        Optional<Event> eventFind = eventRepository.findById(idEvent);
        if (!eventFind.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        if (eventFind.get().getIsWaitApprovalPeriodically() != ApprovalPeriodicallyStatus.DA_GUI_YEU_CAU) {
            throw new RestApiException(Message.INVALID_STATUS);
        }
        eventFind.get().setApprovalPeriociallyReason(reason);
        eventFind.get().setIsWaitApprovalPeriodically(ApprovalPeriodicallyStatus.CHUA_GUI_YEU_CAU);
        eventFind.get().setApproverId(session.getCurrentUserCode());
        eventFind.get().setApproverCode(session.getCurrentUserCode());
        eventRepository.save(eventFind.get());
        StringBuilder messageLog = new StringBuilder("Đã từ chối phê duyệt sự kiện ")
                .append(eventFind.get().getName())
                .append(" thành sự kiện hàng kì");
        loggerUtil.sendLog(messageLog.toString(), eventFind.get().getId());

        MailRequest request = new MailRequest();
        StringBuilder time = new StringBuilder("");
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
        Date startTime = new Date(eventFind.get().getStartTime());
        Date endTime = new Date(eventFind.get().getEndTime());
        if (new Date(sdfDate.format(startTime)).compareTo(new Date(sdfDate.format(endTime))) == 0) {
            time.append("<li>Thời gian diễn ra: ").append(sdfTime.format(startTime)).append(" - ").append(sdfTime.format(endTime)).append("</li><li>Ngày: ").append(sdfDate.format(startTime)).append("</li>");
        } else {
            time.append("<li>Thời gian diễn ra: ").append(sdfDateTime.format(startTime)).append(" - ").append(sdfDateTime.format(endTime)).append("</li>");
        }
        request.setSubject("- Thư thông báo từ chối phê duyệt sự kiện hàng kỳ " + eventFind.get().getName());
        request.setTitle("");
        String htmlBody = MailConstant.CONTENT_EVENT_EDITING.replace("{nameEvent}", eventFind.get().getName())
                .replace("{timeEvent}", time.toString())
                .replace("{nameApprove}", session.getCurrentName())
                .replace("{reason}", eventFind.get().getReason());
        request.setBody(htmlBody);

        // Lấy ds người tổ chức của sự kiện
        List<String> listIdOrganizer = eventOrganizerRepository.getIdOrganizerByIdEvent(eventFind.get().getId());
        List<SimpleResponse> listAllOrganizer = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer);

        List<String> emailsWithId = listAllOrganizer.stream()
                .map(SimpleResponse::getEmailFE)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());

        request.setMails(emailsWithId.toArray(new String[0]));
        emailUtils.sendEmail(request);
        return true;
    }

    /**
     * @param req: Thông tin của comment được gửi lên
     * @return Comment được lưu trong db
     */
    @Override
    public AdminerAllCommentResponse postComment(AdminerPostCommentRequest req) {
        req.setUserId(session.getCurrentIdUser());

        Optional<Event> event = eventRepository.findById(req.getEventId());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Comment comment = new Comment();
        comment.setEventId(req.getEventId());
        comment.setUserId(req.getUserId());
        comment.setComment(req.getComment());
        comment = commentRepository.save(comment);

        SimpleResponse infoUser = callApiIdentity.handleCallApiGetUserById(req.getUserId());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        AdminerAllCommentResponse newComment = new AdminerAllCommentResponse();
        newComment.setId(comment.getId());
        newComment.setLastModifiedDate(dateFormat.format(new Date(comment.getLastModifiedDate())));
        newComment.setComment(comment.getComment());
        newComment.setListReply(new ArrayList<>());
        newComment.setUserId(session.getCurrentUserCode());
        newComment.setEmail(session.getCurrentEmail());
        newComment.setAvatar(infoUser.getPicture());
        loggerUtil.sendLog(infoUser.getUserName() + " đã thêm một bình luận cho sự kiện", event.get().getId());
        return newComment;
    }

    /**
     * @param req: Thông tin comment được gửi lên
     * @return Xóa thành công hoặc không
     */
    @Override
    public Boolean deleteComment(AdminerDeleteCommentRequest req) {
        req.setUserId(session.getCurrentIdUser());

        Optional<Comment> comment = commentRepository.findById(req.getCommentId());
        if (!comment.isPresent()) {
            throw new RestApiException(Message.COMMENT_NOT_EXIST);
        }

        if (comment.get().getReplyId() == null) {
            List<Comment> listReply = commentRepository.getReplyCommentByReplyId(comment.get().getId(), session.getData());
            commentRepository.deleteAll(listReply);
        }
        commentRepository.delete(comment.get());
        loggerUtil.sendLog(session.getCurrentName() + " đã xóa một bình luận trong sự kiện", comment.get().getEventId());
        return true;
    }

    /**
     * @param eventId: Id của sự kiện
     * @return Danh sách kế hoạch của sự kiện
     */
    @Override
    public List<AdminerAgendaItemCustom> getListAgendaItemByEventId(String eventId) {
        List<AdminerAgendaItemsDetailResponse> agendaItemByEventId = agendaItemRepository.getListAgendaItemByEventId(eventId);
        List<String> listIdOrganizer = agendaItemByEventId.stream()
                .map(AdminerAgendaItemsDetailResponse::getOrganizerId)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toList());
        List<SimpleResponse> responseOrganizer = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer);
        List<AdminerAgendaItemCustom> oedAgendaItemCustoms = new ArrayList<>();
        for (AdminerAgendaItemsDetailResponse xx : agendaItemByEventId) {
            AdminerAgendaItemCustom agendaItemCustom = new AdminerAgendaItemCustom();
            agendaItemCustom.setId(xx.getId());
            agendaItemCustom.setStartTime(xx.getStartTime());
            agendaItemCustom.setEndTime(xx.getEndTime());
            agendaItemCustom.setDescription(xx.getDescription());
            agendaItemCustom.setIndex(xx.getIndex());
            if (xx.getOrganizerId() != null) {
                for (SimpleResponse simpleResponse : responseOrganizer) {
                    if (xx.getOrganizerId().equals(simpleResponse.getUserCode())) {
                        agendaItemCustom.setOrganizerId(simpleResponse.getUserCode());
                        agendaItemCustom.setOrganizerName(simpleResponse.getName());
                        agendaItemCustom.setOrganizerUsername(simpleResponse.getUserName());
                        break;
                    }
                }
            }
            oedAgendaItemCustoms.add(agendaItemCustom);
        }
        return oedAgendaItemCustoms;
    }

    /**
     * @param req: Thông tin comment được reply
     * @return Comment được reply lưu trong db
     */
    @Override
    public AdminerReplyCommentResponse replyComment(AdminerReplyCommentRequest req) {
        req.setUserId(session.getCurrentIdUser());
        if (!commentRepository.existsById(req.getReplyId())) {
            throw new RestApiException(Message.COMMENT_NOT_EXIST);
        }
        Optional<Event> event = eventRepository.findById(req.getEventId());
        if (!event.isPresent()) {
            throw new RestApiException(Message.EVENT_NOT_EXIST);
        }
        Comment replyComment = new Comment();
        replyComment.setEventId(req.getEventId());
        replyComment.setComment(req.getComment());
        replyComment.setUserId(req.getUserId());
        replyComment.setReplyId(req.getReplyId());
        replyComment = commentRepository.save(replyComment);

        SimpleResponse responseApprover = callApiIdentity.handleCallApiGetUserById(session.getCurrentIdUser());
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        AdminerReplyCommentResponse commentResponse = new AdminerReplyCommentResponse();
        commentResponse.setId(replyComment.getId());
        commentResponse.setLastModifiedDate(dateFormat.format(new Date(replyComment.getLastModifiedDate())));
        commentResponse.setComment(replyComment.getComment());
        commentResponse.setReplyId(replyComment.getReplyId());
        commentResponse.setUserId(responseApprover.getUserCode());
        commentResponse.setEmail(responseApprover.getEmailFE());
        commentResponse.setAvatar(responseApprover.getPicture());
        loggerUtil.sendLog(responseApprover.getUserName() + " đã thêm một phản hồi bình luận", event.get().getId());
        return commentResponse;
    }

    /**
     * @param eventId: Id sự kiện
     * @return Tài nguyên của sự kiện (link git, record...)
     */
    @Override
    public List<AdminerResourceEventResponce> getResourcesByEventId(String eventId) {
        return resourceReposiotry.getResourcesByEventId(eventId);
    }

    /**
     * @param idevent: Id sự kiện
     * @return Dánh sách địa điểm tổ chức sự kiện
     */
    @Override
    public List<AdminerLocationEventResponse> getLocationByEventId(String idevent) {
        return locationRepository.getLocationByEventId(idevent);
    }

    /**
     * @param idevent: Id sự kiện
     * @return Danh sách đối tượng tham gia sự kiện (kỳ 5, kỳ 2)
     */
    @Override
    public List<AdminerObjectEventResponse> getObjectByEventId(String idevent) {
        return objectRepository.getObjectByEventId(idevent);
    }

    /**
     * @param id: Id sự kiện
     * @return Danh sách bộ môn mà sự kiện hướng đến
     */
    @Override
    public List<AdminerMajorResponse> getMajorByIdEvent(String id) {
        return majorRepository.getMajorByIdEvent(id);
    }

    /**
     * @param req: Thông tin cho sự kiện cần phê duyệt
     * @return Sự kiện đã được phê duyệt hay từ chối
     * @description Phê duyệt hoặc từ chối sự kiện, và gửi mail cho người tổ chức (khi là sự kiện cho giảng viên thì gửi mail thông báo sự kiện sắp tổ chức cho GV)
     */
    @Transactional
    @Override
    public ResponseObject approvalEvent(AdminerEventApproveRequest req) {
        // Thay đổi trạng thái và thêm lý do từ chối sư kiện
        if (req.getId() == null) throw new RestApiException(Message.ID_MUST_NOT_EMPTY);
        Event event = eventRepository.findByIdAndStatusIn(req.getId(), List.of((short) 3, (short) 1, (short) 2, (short) 4, (short) 6))
                .orElseThrow(() -> new RestApiException(Message.EVENT_NOT_EXIST));

        event.setApproverId(session.getCurrentUserCode());
        event.setApproverCode(session.getCurrentUserCode());
        if (req.getStatus() != null && req.getStatus() == EventStatus.APPROVED.ordinal()) {
            event.setStatus(EventStatus.APPROVED);
            event.setReason(req.getReason());
            event.setOrderApprovalPriority(null);
        } else {
            //REASON_IS_EMPTY -> Lý do từ chối trống
            if ("".equals(req.getReason())) throw new RestApiException(Message.REASON_MUST_NOT_EMPTY);
            event.setReason(req.getReason());
            event.setStatus(EventStatus.EDITING);
        }
        ResponseObject responseObject = new ResponseObject(eventRepository.save(event));

        // Gửi mail theo trạng thái
        StringBuilder messageLog = new StringBuilder();
        MailRequest request = new MailRequest();
        StringBuilder time = new StringBuilder("");
        SimpleDateFormat sdfDateTime = new SimpleDateFormat("hh:mm dd/MM/yyyy");
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
        Date startTime = new Date(event.getStartTime());
        Date endTime = new Date(event.getEndTime());
        if (new Date(sdfDate.format(startTime)).compareTo(new Date(sdfDate.format(endTime))) == 0) {
            time.append("<li>Thời gian: ").append(sdfTime.format(startTime)).append(" - ").append(sdfTime.format(endTime)).append("</li><li>Ngày: ").append(sdfDate.format(startTime)).append("</li>");
        } else {
            time.append("<li>Thời gian: ").append(sdfDateTime.format(startTime)).append(" - ").append(sdfDateTime.format(endTime)).append("</li>");
        }
        if (event.getStatus() == EventStatus.EDITING) {
            request.setSubject("- Thư thông báo từ chối sự kiện " + event.getName());
            request.setTitle("");
            String htmlBody = MailConstant.CONTENT_EVENT_EDITING.replace("{nameEvent}", event.getName())
                    .replace("{timeEvent}", time.toString())
                    .replace("{nameApprove}", session.getCurrentName())
                    .replace("{reason}", event.getReason() != null ? event.getReason() : "");
            request.setBody(htmlBody);
            messageLog.append("Đã từ chối sự kiện ")
                    .append(event.getName());
        }
        if (event.getStatus() == EventStatus.APPROVED) {
            request.setSubject("- Thư thông báo phê duyệt sự kiện " + event.getName());
            request.setTitle("");
            String htmlBody = MailConstant.CONTENT_EVENT_APPROVE.replace("{nameEvent}", event.getName())
                    .replace("{timeEvent}", time.toString())
                    .replace("{nameApprove}", session.getCurrentName());
            request.setBody(htmlBody);
            messageLog.append("Đã phê duyệt sự kiện ")
                    .append(event.getName());
        }
        // Lấy ds người tổ chức
        List<SimpleResponse> listAllOrganizer = callApiIdentity.handleCallApiGetUserByRoleAndModule(ActorConstants.ACTOR_GV, event.getId());
        List<String> listIdOrganizer = eventOrganizerRepository.getIdOrganizerByIdEvent(event.getId());

        List<String> emailsWithId = new ArrayList<>(); // Ds người tổ chức trong sự kiện
        List<String> emailsWithoutId = new ArrayList<>(); // Ds giảng viên không là người tổ chức của sk
        List<SimpleResponse> listOrganizer = new ArrayList<>();
        for (SimpleResponse organizer : listAllOrganizer) {
            String organizerId = organizer.getUserCode();
            String organizerEmail = organizer.getEmailFE();
            if (listIdOrganizer.contains(organizerId)) {
                listOrganizer.add(organizer);
                emailsWithId.add(organizerEmail);
            } else {
                emailsWithoutId.add(organizerEmail);
            }
        }
        request.setMails(emailsWithId.toArray(new String[0]));
        emailUtils.sendEmail(request);
        loggerUtil.sendLog(messageLog.toString(), event.getId());
        if (event.getEventType() == EventType.LECTURER_EVENT) {
            StringBuilder organizer = new StringBuilder("");
            StringBuilder eventResource = new StringBuilder();
            StringBuilder eventLocation = new StringBuilder();
            StringBuilder eventImage = new StringBuilder();
            List<AdminerLocationEventResponse> locationList = locationRepository.getLocationByEventId(event.getId());
            List<AdminerResourceEventResponce> resourceList = resourceReposiotry.getResourcesByEventId(event.getId());

            //Lấy ra thông tin người tổ chức
            for (SimpleResponse item : listOrganizer) {
                organizer.append("<li>").append(item.getUserName()).append(" - ").append(item.getName()).append("</li>");
            }
            // Tạo HTML cho các địa điểm
            if (locationList != null) {
                for (AdminerLocationEventResponse item : locationList) {
                    if (item.getFormality().equals("ONLINE")) {
                        eventLocation.append("<li>ONLINE - " + item.getName() + ": " + "<a href=\"" + item.getPath() + "\" >Tại đây</a></li>");
                    } else {
                        eventLocation.append("<li>OFFLINE - " + item.getName() + ": " + item.getPath());
                    }
                }
            }
            //Kiểm tra và tạo HTMl cho background và banner của sự kiện nếu có
            if (event.getBackgroundPath() != null) {
                eventImage.append("<li>Link Background: <a href=\"").append(event.getBackgroundPath()).append("\">Tại đây</a></li>");
            }
            if (event.getBannerPath() != null) {
                eventImage.append("<li>Link Banner: <a href=\"").append(event.getBannerPath()).append("\">Tại đây</a></li>");
            }
            // Tạo HTML cho các Resource đã lọc
            if (resourceList != null) {
                eventResource.append("<li>Tài nguyên: <ul>");
                for (AdminerResourceEventResponce item : resourceList) {
                    eventResource.append("<li>" + item.getName() + ": " + "<a href=\"" + item.getPath() + "\">Tại đây</a></li>");
                }
                eventResource.append("</ul></li>");
            }
            String catrgoryName = eventRepository.getCategoryByIdEvent(event.getId());
            MailAnnounceUpcomingEvent x = new MailAnnounceUpcomingEvent();
            x.setMails(emailsWithoutId.toArray(new String[0]));
            x.setSubject(MailConstant.SUBJECT + "- Thư tham dự sự kiện " + catrgoryName + " - " + event.getName());
            x.setDate(time.toString());
            x.setEventCategory(catrgoryName);
            x.setEventName(event.getName());
            x.setLocations(eventLocation.toString());
            x.setImage(eventImage.toString());
            x.setResources(eventResource.toString());
            x.setLecturerList(organizer.toString());
            emailUtils.sendMailAnnounceUpcomingEvent(x);
        }
        responseObject.setMessage("update success");
        return responseObject;
    }

    @Override
    public List<SimpleResponse> getListOrganizerByIdEvent(String idEvent) {
        List<String> listIdOrganizer = eventOrganizerRepository.getIdOrganizerByIdEvent(idEvent);
        List<SimpleResponse> listOrganizerInEvent = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer);
        List<EventOrganizer> eventOrganizerList = eventOrganizerRepository.findAllByEventId(idEvent);
        for (SimpleResponse simpleResponse : listOrganizerInEvent){
            for (EventOrganizer eventOrganizer : eventOrganizerList){
                if(simpleResponse.getUserCode().equalsIgnoreCase(eventOrganizer.getOrganizerId())){
                    simpleResponse.setMeetingHour(eventOrganizer.getMeetingHour());
                }
            }
        }
        return listOrganizerInEvent;
    }

    @Override
    public String getNameEventsInTime(String id, Long startTime, Long endTime) {
        return eventRepository.getNameEventsInTime(id, startTime, endTime, session.getData());
    }
}
