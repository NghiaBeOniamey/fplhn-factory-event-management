package com.portalevent.core.approver.eventclosed.service.impl;

import com.portalevent.core.approver.eventclosed.model.request.AecEventCloseRequest;
import com.portalevent.core.approver.eventclosed.model.response.AecEventCloseResponse;
import com.portalevent.core.approver.eventclosed.model.response.AecPropsResponse;
import com.portalevent.core.approver.eventclosed.repository.AecCategoryRepository;
import com.portalevent.core.approver.eventclosed.repository.AecEventCloseRepository;
import com.portalevent.core.approver.eventclosed.repository.AecMajorRepository;
import com.portalevent.core.approver.eventclosed.repository.AecObjectRepository;
import com.portalevent.core.approver.eventclosed.repository.AecSemesterRepository;
import com.portalevent.core.approver.eventclosed.service.AecEventClosedService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AecEventClosedServiceImpl implements AecEventClosedService {
    @Autowired
    private AecEventCloseRepository repository;
    @Autowired
    private AecMajorRepository majorRepository;
    @Autowired
    private AecObjectRepository objectRepository;
    @Autowired
    private AecCategoryRepository categoryRepository;
    @Autowired
    private AecSemesterRepository semesterRepository;
    @Autowired
    private PortalEventsSession session;

    /**
     *
     * @param request tên, thể loại, đối tượng, bộ môn, học kỳ
     * @return danh sách sự kiện đã đóng
     */
    @Override
    public PageableObject<AecEventCloseResponse> getAllEventClose(AecEventCloseRequest request) {
        replaceAll(request);
        return new PageableObject<>(repository.getAllEventClose(PageRequest.of(request.getPage(), request.getSize()), request, session.getData()));
    }

    private static void replaceAll(AecEventCloseRequest request) {
        String regexTrim = "\\s+";
        request.setName(request.getName().replaceAll(regexTrim, " "));
        request.setMajor(request.getMajor().replaceAll(regexTrim, " "));
        request.setCategory(request.getCategory().replaceAll(regexTrim, " "));
        request.setObject(request.getObject().replaceAll(regexTrim, " "));
        request.setSemester(request.getSemester().replaceAll(regexTrim, " "));
    }

    /**
     *
     * @return danh sách bộ môn
     */
    @Override
    public List<AecPropsResponse> getAllMajor() {
        return majorRepository.getAllMajor();
    }

    /**
     *
     * @return danh sách đối tượng
     */
    @Override
    public List<AecPropsResponse> getAllObject() {
        return objectRepository.getAllObject();
    }

    /**
     *
     * @return danh sách thể loại
     */
    @Override
    public List<AecPropsResponse> getAllCategory() {
        return categoryRepository.getAllCategory();
    }

    /**
     *
     * @return danh sách học kỳ
     */
    @Override
    public List<AecPropsResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }
}
