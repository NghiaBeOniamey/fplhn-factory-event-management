package com.portalevent.core.adminh.statisticseventmanagement.service.impl;

import com.portalevent.core.adminh.statisticseventmanagement.model.request.AdminHIdRequest;
import com.portalevent.core.adminh.statisticseventmanagement.model.request.AdminHSemesterMajorRequest;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseCategory;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseEventInMajor;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseEventInMajorResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseEventInSemesterResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseLecturerInEvent;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseListOrganizer;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseListOrganizerResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseDepartmentResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseParticipantInEvent;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseSemesterResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseTopEventResponse;
import com.portalevent.core.adminh.statisticseventmanagement.repository.AdminHseCategoryRepository;
import com.portalevent.core.adminh.statisticseventmanagement.repository.AdminHseEventRepository;
import com.portalevent.core.adminh.statisticseventmanagement.repository.AdminHseDepartmentRepository;
import com.portalevent.core.adminh.statisticseventmanagement.repository.AdminHseSemesterRepository;
import com.portalevent.core.adminh.statisticseventmanagement.service.AdminHseStatisticEventService;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.CallApiIdentity;
import com.portalevent.util.mail.EmailUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HoangDV
 */
@Service
public class AdminHseSemesterEventServiceImpl implements AdminHseStatisticEventService {

    private final AdminHseSemesterRepository semesterRepository;
    private final AdminHseEventRepository eventRepository;
    private final AdminHseDepartmentRepository majorRepository;
    private final AdminHseCategoryRepository categoryRepository;
    private final CallApiIdentity callApiIdentity;
    private final EmailUtils emailUtils;
    private final PortalEventsSession session;

    public AdminHseSemesterEventServiceImpl(AdminHseSemesterRepository semesterRepository, AdminHseEventRepository eventRepository, AdminHseDepartmentRepository majorRepository, AdminHseCategoryRepository categoryRepository, CallApiIdentity callApiIdentity, EmailUtils emailUtils, PortalEventsSession session) {
        this.semesterRepository = semesterRepository;
        this.eventRepository = eventRepository;
        this.majorRepository = majorRepository;
        this.categoryRepository = categoryRepository;
        this.callApiIdentity = callApiIdentity;
        this.emailUtils = emailUtils;
        this.session = session;
    }

    /**
     * @return List semester
     * @description Lấy ra tất cả học kỳ
     */
    @Override
    public List<AdminHseSemesterResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }

    @Override
    public List<AdminHseDepartmentResponse> getAllDepartment() {
        return majorRepository.getAllDepartment(session.getData());
    }

    /**
     * @param request: Id của học kỳ
     * @return Tổng số sự kiện đã đăng ký trong kỳ ở tất cả các trạng thái
     */
    @Override
    public Integer getSumEventBySemester(AdminHSemesterMajorRequest request) {
        return eventRepository.getSumEventBySemester(request, session.getData());
    }

    /**
     * @param request: Id của học kỳ
     * @return Trạng thái của các sự kiện và số lượng tương ứng
     * @description Ví dụ lấy ra trạng thái 0 là đã đóng với số lượng 2
     */
    @Override
    public List<AdminHseEventInSemesterResponse> getEventBySemester(AdminHSemesterMajorRequest request) {
        return eventRepository.getEventBySemester(request, session.getData());
    }

    /**
     * @param request: Id của học kỳ
     * @return Thông tin của top 3 sự kiện có nhiều người tham gia nhất
     */
    @Override
    public List<AdminHseTopEventResponse> getTopEvent(AdminHSemesterMajorRequest request) {
        return eventRepository.getTopEvent(request, session.getData());
    }

    /**
     * @param request: Id của học kỳ
     * @return List người tổ chức sự kiện
     * @description Trả về thông tin của người tổ chức và số lượng sự kiện đã đăng kí trong kì
     */
    @Override
    public List<AdminHseListOrganizerResponse> getListOrganizer(AdminHSemesterMajorRequest request) {
        // List object gồm 2 field idOrganizer and quantityEvent
        List<AdminHseListOrganizer> listOrganizer = eventRepository.getListOrganizer(request, session.getData());
        // List response gồm 3 field nameOrganizer, email, quantityEvent
        List<AdminHseListOrganizerResponse> listOrganizerResponses = new ArrayList<>();

        List<String> listIdOrganizer = new ArrayList<>();
        for (AdminHseListOrganizer o : listOrganizer) {
            listIdOrganizer.add(o.getOrganizerId());
        }
        List<SimpleResponse> listSimpleResponse = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer);

        for (AdminHseListOrganizer o : listOrganizer) {
            for (SimpleResponse x : listSimpleResponse) {
                if (o.getOrganizerId().equals(x.getUserCode())) {
                    AdminHseListOrganizerResponse response = new AdminHseListOrganizerResponse();
                    response.setName(x.getName());
                    response.setEmail(x.getEmailFE());
                    response.setQuantityEvent(o.getQuantityEvent());
                    listOrganizerResponses.add(response);
                }
            }
        }
        return listOrganizerResponses;
    }

    /**
     * @param request: Id học kỳ
     * @return Danh sách bộ môn kèm sô sự kiện đã tổ chức trong truyên ngành trong học kỳ
     */
    @Override
    public List<AdminHseEventInMajorResponse> getEventInMajorByIdSemester(AdminHSemesterMajorRequest request) {
        List<AdminHseEventInMajor> listEventInMajor = eventRepository.getEventInMajorByIdSemester(request);
        List<AdminHseDepartmentResponse> listMajor = majorRepository.getAllDepartment(session.getData());
        // List real trả về được kết hợp thông tin từ hai list trên
        List<AdminHseEventInMajorResponse> listResponses = new ArrayList<>();

        for (AdminHseDepartmentResponse x : listMajor) {
            int check = 0;
            AdminHseEventInMajorResponse response = new AdminHseEventInMajorResponse();
            response.setCode(x.getCode());
            response.setName(x.getName());
            for (AdminHseEventInMajor y : listEventInMajor) {
                if (y.getCode().equals(x.getCode())) {
                    response.setQuantity(y.getQuantity());
                    check = 1;
                }
            }
            if (check == 0) {
                response.setQuantity(0);
            }
            listResponses.add(response);
        }
        return listResponses;
    }

    /**
     * @param request: Id học kỳ
     * @return Trả về danh sách gồm các sự kiện gồm số lượng người dự kiện tham gia,
     * người tham gia thực tế, người đăng kí
     */
    @Override
    public List<AdminHseParticipantInEvent> getListParticipantInEvent(AdminHSemesterMajorRequest request) {
        return eventRepository.getListParticipantInEvent(request, session.getData());
    }

    /**
     * @param request: Id học kỳ
     * @return Trả về danh sách sự kiện gồm số lượng người dự kiện tham gia,
     * người tham gia thực tế, người đăng kí theo thể loại
     */
    @Override
    public List<AdminHseParticipantInEvent> getListParticipantInEventByCategory(AdminHIdRequest request) {
        return eventRepository.getListParticipantInEventByCategory(request, session.getData());
    }

    /**
     * @param request: Id học kỳ
     * @return Trả về danh sách sự kiện gồm số lượng đăng ký, số lượng tham gia thực tế
     */
    @Override
    public List<AdminHseLecturerInEvent> getListLecturerInEvent(AdminHSemesterMajorRequest request) {
        return eventRepository.getListLecturerInEvent(request, session.getData());
    }

    /**
     * @return Danh sách thể loại sự kiện
     */
    @Override
    public List<AdminHseCategory> getAllCategory() {
        return categoryRepository.getAllCategory();
    }
}
