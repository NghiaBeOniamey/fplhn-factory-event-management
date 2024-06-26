package com.portalevent.core.adminho.statisticseventmanagement.service.impl;

import com.portalevent.core.adminho.statisticseventmanagement.model.request.AdminHOIdRequest;
import com.portalevent.core.adminho.statisticseventmanagement.model.request.AdminHOSCDRequest;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseCampusResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseCategory;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseDepartmentResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseEventInDepartment;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseEventInMajorResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseEventInSemesterResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseLecturerInEvent;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseListOrganizer;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseListOrganizerResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseMajorResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseParticipantInEvent;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseSemesterResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseTopEventResponse;
import com.portalevent.core.adminho.statisticseventmanagement.repository.AdminHOseCampusRepository;
import com.portalevent.core.adminho.statisticseventmanagement.repository.AdminHOseCategoryRepository;
import com.portalevent.core.adminho.statisticseventmanagement.repository.AdminHOseDepartmentRepository;
import com.portalevent.core.adminho.statisticseventmanagement.repository.AdminHOseEventRepository;
import com.portalevent.core.adminho.statisticseventmanagement.repository.AdminHOseMajorRepository;
import com.portalevent.core.adminho.statisticseventmanagement.repository.AdminHOseSemesterRepository;
import com.portalevent.core.adminho.statisticseventmanagement.service.AdminHOseStatisticEventService;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.util.CallApiIdentity;
import com.portalevent.util.mail.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author HoangDV
 */
@Service
@Slf4j
public class AdminHOseSemesterEventServiceImpl implements AdminHOseStatisticEventService {

    private final AdminHOseSemesterRepository semesterRepository;
    private final AdminHOseEventRepository eventRepository;
    private final AdminHOseMajorRepository majorRepository;
    private final AdminHOseCategoryRepository categoryRepository;
    private final CallApiIdentity callApiIdentity;
    private final EmailUtils emailUtils;
    private AdminHOseCampusRepository campusRepository;
    private AdminHOseDepartmentRepository departmentRepository;

    public AdminHOseSemesterEventServiceImpl(AdminHOseSemesterRepository semesterRepository,
                                             AdminHOseEventRepository eventRepository,
                                             AdminHOseMajorRepository majorRepository,
                                             AdminHOseCategoryRepository categoryRepository,
                                             AdminHOseCampusRepository campusRepository,
                                             AdminHOseDepartmentRepository departmentRepository,
                                             CallApiIdentity callApiIdentity, EmailUtils emailUtils) {
        this.semesterRepository = semesterRepository;
        this.eventRepository = eventRepository;
        this.majorRepository = majorRepository;
        this.categoryRepository = categoryRepository;
        this.campusRepository = campusRepository;
        this.departmentRepository = departmentRepository;
        this.callApiIdentity = callApiIdentity;
        this.emailUtils = emailUtils;
    }

    /**
     * @return List semester
     * @description Lấy ra tất cả học kỳ
     */
    @Override
    public List<AdminHOseSemesterResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }

    @Override
    public List<AdminHOseCampusResponse> getAllCampus() {
        return campusRepository.getCampusList();
    }

    @Override
    public List<AdminHOseDepartmentResponse> getDepartmentByCampusId(Long campusId) {
        return departmentRepository.getDepartmentByCampusId(campusId);
    }

    /**
     * @param request: Id của học kỳ
     * @return Tổng số sự kiện đã đăng ký trong kỳ ở tất cả các trạng thái
     */
    @Override
    public Integer getSumEventBySemester(AdminHOSCDRequest request) {
        return eventRepository.getSumEventBySemester(request);
    }

    /**
     * @param request: Id của học kỳ
     * @return Trạng thái của các sự kiện và số lượng tương ứng
     * @description Ví dụ lấy ra trạng thái 0 là đã đóng với số lượng 2
     */
    @Override
    public List<AdminHOseEventInSemesterResponse> getEventBySemester(AdminHOSCDRequest request) {
        return eventRepository.getEventBySemester(request);
    }

    /**
     * @param request: Id của học kỳ
     * @return Thông tin của top 3 sự kiện có nhiều người tham gia nhất
     */
    @Override
    public List<AdminHOseTopEventResponse> getTopEvent(AdminHOSCDRequest request) {
        return eventRepository.getTopEvent(request);
    }

    /**
     * @param request: Id của học kỳ
     * @return List người tổ chức sự kiện
     * @description Trả về thông tin của người tổ chức và số lượng sự kiện đã đăng kí trong kì
     */
    @Override
    public List<AdminHOseListOrganizerResponse> getListOrganizer(AdminHOSCDRequest request) {
        // List object gồm 2 field idOrganizer and quantityEvent
        List<AdminHOseListOrganizer> listOrganizer = eventRepository.getListOrganizer(request);
        // List response gồm 3 field nameOrganizer, email, quantityEvent
        List<AdminHOseListOrganizerResponse> listOrganizerResponses = new ArrayList<>();

        List<String> listIdOrganizer = new ArrayList<>();
        for (AdminHOseListOrganizer o : listOrganizer) {
            listIdOrganizer.add(o.getOrganizerId());
        }
        List<SimpleResponse> listSimpleResponse = callApiIdentity.handleCallApiGetListUserByListId(listIdOrganizer);

        for (AdminHOseListOrganizer o : listOrganizer) {
            for (SimpleResponse x : listSimpleResponse) {
                if (o.getOrganizerId().equals(x.getUserCode())) {
                    AdminHOseListOrganizerResponse response = new AdminHOseListOrganizerResponse();
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
    public List<AdminHOseEventInMajorResponse> getEventInMajorByIdSemester(AdminHOSCDRequest request) {
        List<AdminHOseEventInDepartment> listEventInMajor = eventRepository.getEventInMajorByIdSemester(request);
        List<AdminHOseMajorResponse> listMajor = majorRepository.getAllMajor(request);
        // List real trả về được kết hợp thông tin từ hai list trên
        List<AdminHOseEventInMajorResponse> listResponses = new ArrayList<>();

        for (AdminHOseMajorResponse x : listMajor) {
            int check = 0;
            AdminHOseEventInMajorResponse response = new AdminHOseEventInMajorResponse();
            response.setCode(x.getCode());
            response.setName(x.getName());
            for (AdminHOseEventInDepartment y : listEventInMajor) {
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
    public List<AdminHOseParticipantInEvent> getListParticipantInEvent(AdminHOSCDRequest request) {
        return eventRepository.getListParticipantInEvent(request);
    }

    /**
     * @param request: Id học kỳ
     * @return Trả về danh sách sự kiện gồm số lượng người dự kiện tham gia,
     * người tham gia thực tế, người đăng kí theo thể loại
     */
    @Override
    public List<AdminHOseParticipantInEvent> getListParticipantInEventByCategory(AdminHOIdRequest request) {
        return eventRepository.getListParticipantInEventByCategory(request);
    }

    /**
     * @param request: Id học kỳ
     * @return Trả về danh sách sự kiện gồm số lượng đăng ký, số lượng tham gia thực tế
     */
    @Override
    public List<AdminHOseLecturerInEvent> getListLecturerInEvent(AdminHOSCDRequest request) {
        return eventRepository.getListLecturerInEvent(request);
    }

    /**
     * @return Danh sách thể loại sự kiện
     */
    @Override
    public List<AdminHOseCategory> getAllCategory() {
        return categoryRepository.getAllCategory();
    }
}
