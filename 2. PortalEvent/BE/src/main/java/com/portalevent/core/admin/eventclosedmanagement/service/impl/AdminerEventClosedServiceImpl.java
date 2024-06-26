package com.portalevent.core.admin.eventclosedmanagement.service.impl;

import com.portalevent.core.admin.eventclosedmanagement.model.request.AdminerEventCloseRequest;
import com.portalevent.core.admin.eventclosedmanagement.model.response.AdminerEventCloseResponse;
import com.portalevent.core.admin.eventclosedmanagement.model.response.AdminerPropsResponse;
import com.portalevent.core.admin.eventclosedmanagement.repository.*;
import com.portalevent.core.admin.eventclosedmanagement.service.AdminerEventClosedService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminerEventClosedServiceImpl implements AdminerEventClosedService {

    private final AdminerEventCloseRepository repository;
    private final AdminerCMajorRepository majorRepository;
    private final AdminerCObjectRepository objectRepository;
    private final AdminerCategoryRepository categoryRepository;
    private final AdminerCSemesterRepository semesterRepository;
    private final PortalEventsSession session;

    public AdminerEventClosedServiceImpl(AdminerEventCloseRepository repository, AdminerCMajorRepository majorRepository, AdminerCObjectRepository objectRepository, AdminerCategoryRepository categoryRepository, AdminerCSemesterRepository semesterRepository, PortalEventsSession session) {
        this.repository = repository;
        this.majorRepository = majorRepository;
        this.objectRepository = objectRepository;
        this.categoryRepository = categoryRepository;
        this.semesterRepository = semesterRepository;
        this.session = session;
    }

    /**
     *
     * @param request tên, thể loại, đối tượng, bộ môn, học kỳ
     * @return danh sách sự kiện đã đóng
     */
    @Override
    public PageableObject<AdminerEventCloseResponse> getAllEventClose(AdminerEventCloseRequest request) {
        return new PageableObject<>(repository.getAllEventClose(PageRequest.of(request.getPage(), request.getSize()), request, session.getData()));
    }

    /**
     *
     * @return danh sách bộ môn
     */
    @Override
    public List<AdminerPropsResponse> getAllMajor() {
        return majorRepository.getAllMajor();
    }

    /**
     *
     * @return danh sách đối tượng
     */
    @Override
    public List<AdminerPropsResponse> getAllObject() {
        return objectRepository.getAllObject();
    }

    /**
     *
     * @return danh sách thể loại
     */
    @Override
    public List<AdminerPropsResponse> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    /**
     *
     * @return danh sách học kỳ
     */
    @Override
    public List<AdminerPropsResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }

}
