package com.portalevent.core.adminh.eventclosedmanagement.service.impl;

import com.portalevent.core.adminh.eventclosedmanagement.model.request.AdminHecEventCloseRequest;
import com.portalevent.core.adminh.eventclosedmanagement.model.response.AdminHecEventCloseResponse;
import com.portalevent.core.adminh.eventclosedmanagement.model.response.AdminHecPropsResponse;
import com.portalevent.core.adminh.eventclosedmanagement.repository.AdminHecCategoryRepository;
import com.portalevent.core.adminh.eventclosedmanagement.repository.AdminHecEventCloseRepository;
import com.portalevent.core.adminh.eventclosedmanagement.repository.AdminHecMajorRepository;
import com.portalevent.core.adminh.eventclosedmanagement.repository.AdminHecObjectRepository;
import com.portalevent.core.adminh.eventclosedmanagement.repository.AdminHdecSemesterRepository;
import com.portalevent.core.adminh.eventclosedmanagement.service.AdminHecEventClosedService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHecEventClosedServiceImpl implements AdminHecEventClosedService {

    private final AdminHecEventCloseRepository repository;
    private final AdminHecMajorRepository majorRepository;
    private final AdminHecObjectRepository objectRepository;
    private final AdminHecCategoryRepository categoryRepository;
    private final AdminHdecSemesterRepository semesterRepository;
    private final PortalEventsSession session;

    public AdminHecEventClosedServiceImpl(AdminHecEventCloseRepository repository, AdminHecMajorRepository majorRepository, AdminHecObjectRepository objectRepository, AdminHecCategoryRepository categoryRepository, AdminHdecSemesterRepository semesterRepository, PortalEventsSession session) {
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
    public PageableObject<AdminHecEventCloseResponse> getAllEventClose(AdminHecEventCloseRequest request) {
        return new PageableObject<>(repository.getAllEventClose(PageRequest.of(request.getPage(), request.getSize()), request, session.getData()));
    }

    /**
     *
     * @return danh sách bộ môn
     */
    @Override
    public List<AdminHecPropsResponse> getAllMajor() {
        return majorRepository.getAllMajor();
    }

    /**
     *
     * @return danh sách đối tượng
     */
    @Override
    public List<AdminHecPropsResponse> getAllObject() {
        return objectRepository.getAllObject();
    }

    /**
     *
     * @return danh sách thể loại
     */
    @Override
    public List<AdminHecPropsResponse> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    /**
     *
     * @return danh sách học kỳ
     */
    @Override
    public List<AdminHecPropsResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }

}
