package com.portalevent.core.admin.statisticseventmanagement.service.impl;

import com.portalevent.core.admin.statisticseventmanagement.model.request.AdminerIdRequest;
import com.portalevent.core.admin.statisticseventmanagement.model.request.AdminerSemesterMajorRequest;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerCategory;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerEventInMajor;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerEventInMajorResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerEventInSemesterResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerLecturerInEvent;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerListOrganizer;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerListOrganizerResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerDepartmentResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerParticipantInEvent;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerSemesterResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerTopEventResponse;
import com.portalevent.core.admin.statisticseventmanagement.repository.AdminerseCategoryRepository;
import com.portalevent.core.admin.statisticseventmanagement.repository.AdminerseEventRepository;
import com.portalevent.core.admin.statisticseventmanagement.repository.AdminerseDepartmentRepository;
import com.portalevent.core.admin.statisticseventmanagement.repository.AdminerseSemesterRepository;
import com.portalevent.core.admin.statisticseventmanagement.service.AdminerseStatisticEventService;
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
public class AdminerseSemesterEventServiceImpl implements AdminerseStatisticEventService {

    private final AdminerseSemesterRepository semesterRepository;
    private final AdminerseEventRepository eventRepository;
    private final AdminerseDepartmentRepository departmentRepository;
    private final AdminerseCategoryRepository categoryRepository;
    private final CallApiIdentity callApiIdentity;
    private final EmailUtils emailUtils;
    private final PortalEventsSession session;

    public AdminerseSemesterEventServiceImpl(AdminerseSemesterRepository semesterRepository, AdminerseEventRepository eventRepository, AdminerseDepartmentRepository departmentRepository, AdminerseCategoryRepository categoryRepository, CallApiIdentity callApiIdentity, EmailUtils emailUtils, PortalEventsSession session) {
        this.semesterRepository = semesterRepository;
        this.eventRepository = eventRepository;
        this.departmentRepository = departmentRepository;
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
    public List<AdminerSemesterResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }

    @Override
    public List<AdminerDepartmentResponse> getAllDepartment() {
        return departmentRepository.getAllDepartment(session.getData());
    }

    /**
     * @param request: Id của học kỳ
     * @return Tổng số sự kiện đã đăng ký trong kỳ ở tất cả các trạng thái
     */
    @Override
    public Integer getSumEventBySemester(AdminerSemesterMajorRequest request) {
        return eventRepository.getSumEventBySemester(request, session.getData());
    }

    /**
     * @param request: Id của học kỳ
     * @return Trạng thái của các sự kiện và số lượng tương ứng
     * @description Ví dụ lấy ra trạng thái 0 là đã đóng với số lượng 2
     */
    @Override
    public List<AdminerEventInSemesterResponse> getEventBySemester(AdminerSemesterMajorRequest request) {
        return eventRepository.getEventBySemester(request, session.getData());
    }

    /**
     * @param request: Id của học kỳ
     * @return Thông tin của top 3 sự kiện có nhiều người tham gia nhất
     */
    @Override
    public List<AdminerTopEventResponse> getTopEvent(AdminerSemesterMajorRequest request) {
        return eventRepository.getTopEvent(request, session.getData());
    }

    /**
     * @param request: Id của học kỳ
     * @return List người tổ chức sự kiện
     * @description Trả về thông tin của người tổ chức và số lượng sự kiện đã đăng kí trong kì
     */
    @Override
    public List<AdminerListOrganizerResponse> getListOrganizer(AdminerSemesterMajorRequest request) {
        // List object gồm 2 field idOrganizer and quantityEvent
        List<AdminerListOrganizer> listOrganizer = eventRepository.getListOrganizer(request, session.getData());
        // List response gồm 3 field nameOrganizer, email, quantityEvent
        List<AdminerListOrganizerResponse> listOrganizerResponses = new ArrayList<>();

        List<String> listIdOrganizer = new ArrayList<>();
        for (AdminerListOrganizer o : listOrganizer) {
            listIdOrganizer.add(o.getOrganizerId());
        }
        List<SimpleResponse> listSimpleResponse = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer);

        for (AdminerListOrganizer o : listOrganizer) {
            for (SimpleResponse x : listSimpleResponse) {
                if (o.getOrganizerId().equals(x.getUserCode())) {
                    AdminerListOrganizerResponse response = new AdminerListOrganizerResponse();
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
    public List<AdminerEventInMajorResponse> getEventInMajorByIdSemester(AdminerSemesterMajorRequest request) {
        List<AdminerEventInMajor> listEventInMajor = eventRepository.getEventInMajorByIdSemester(request);
        List<AdminerDepartmentResponse> listMajor = departmentRepository.getAllDepartment(session.getData());
        // List real trả về được kết hợp thông tin từ hai list trên
        List<AdminerEventInMajorResponse> listResponses = new ArrayList<>();

        for (AdminerDepartmentResponse x : listMajor) {
            int check = 0;
            AdminerEventInMajorResponse response = new AdminerEventInMajorResponse();
            response.setCode(x.getCode());
            response.setName(x.getName());
            for (AdminerEventInMajor y : listEventInMajor) {
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
    public List<AdminerParticipantInEvent> getListParticipantInEvent(AdminerSemesterMajorRequest request) {
        return eventRepository.getListParticipantInEvent(request, session.getData());
    }

    /**
     * @param request: Id học kỳ
     * @return Trả về danh sách sự kiện gồm số lượng người dự kiện tham gia,
     * người tham gia thực tế, người đăng kí theo thể loại
     */
    @Override
    public List<AdminerParticipantInEvent> getListParticipantInEventByCategory(AdminerIdRequest request) {
        return eventRepository.getListParticipantInEventByCategory(request, session.getData());
    }

    /**
     * @param request: Id học kỳ
     * @return Trả về danh sách sự kiện gồm số lượng đăng ký, số lượng tham gia thực tế
     */
    @Override
    public List<AdminerLecturerInEvent> getListLecturerInEvent(AdminerSemesterMajorRequest request) {
        return eventRepository.getListLecturerInEvent(request, session.getData());
    }

    /**
     * @return Danh sách thể loại sự kiện
     */
    @Override
    public List<AdminerCategory> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

}
