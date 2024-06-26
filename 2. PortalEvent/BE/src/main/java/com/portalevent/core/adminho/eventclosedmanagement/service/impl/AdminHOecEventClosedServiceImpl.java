package com.portalevent.core.adminho.eventclosedmanagement.service.impl;

import com.portalevent.core.adminho.eventclosedmanagement.model.request.AdminHOecEventCloseRequest;
import com.portalevent.core.adminho.eventclosedmanagement.model.response.AdminHOecEventCloseResponse;
import com.portalevent.core.adminho.eventclosedmanagement.model.response.AdminHOecPropsResponse;
import com.portalevent.core.adminho.eventclosedmanagement.repository.AdminHOdecSemesterRepository;
import com.portalevent.core.adminho.eventclosedmanagement.repository.AdminHOecCategoryRepository;
import com.portalevent.core.adminho.eventclosedmanagement.repository.AdminHOecEventCloseRepository;
import com.portalevent.core.adminho.eventclosedmanagement.repository.AdminHOecMajorRepository;
import com.portalevent.core.adminho.eventclosedmanagement.repository.AdminHOecObjectRepository;
import com.portalevent.core.adminho.eventclosedmanagement.service.AdminHOecEventClosedService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminHOecEventClosedServiceImpl implements AdminHOecEventClosedService {

    private final AdminHOecEventCloseRepository repository;
    private final AdminHOecMajorRepository majorRepository;
    private final AdminHOecObjectRepository objectRepository;
    private final AdminHOecCategoryRepository categoryRepository;
    private final AdminHOdecSemesterRepository semesterRepository;
    private final PortalEventsSession session;

    public AdminHOecEventClosedServiceImpl(AdminHOecEventCloseRepository repository, AdminHOecMajorRepository majorRepository, AdminHOecObjectRepository objectRepository, AdminHOecCategoryRepository categoryRepository, AdminHOdecSemesterRepository semesterRepository, PortalEventsSession session) {
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
    public PageableObject<AdminHOecEventCloseResponse> getAllEventClose(AdminHOecEventCloseRequest request) {
        return new PageableObject<>(repository.getAllEventClose(PageRequest.of(request.getPage(), request.getSize()), request, session.getData()));
    }

    /**
     *
     * @return danh sách bộ môn
     */
    @Override
    public List<AdminHOecPropsResponse> getAllMajor() {
        return majorRepository.getAllMajor();
    }

    /**
     *
     * @return danh sách đối tượng
     */
    @Override
    public List<AdminHOecPropsResponse> getAllObject() {
        return objectRepository.getAllObject();
    }

    /**
     *
     * @return danh sách thể loại
     */
    @Override
    public List<AdminHOecPropsResponse> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    /**
     *
     * @return danh sách học kỳ
     */
    @Override
    public List<AdminHOecPropsResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }

}
