package com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHewCommentEventDetailRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.request.AdminHewEventListRequest;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHewCommentEventDetailResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone.AdminHewEventListResponse;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.repository.AdminHewaEventRepository;
import com.portalevent.core.adminh.eventwaitingapprovalmanagement.service.AdminHewEventService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.infrastructure.constant.Message;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.util.LoggerUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminHewaEventServiceImpl implements AdminHewEventService {

    private final AdminHewaEventRepository apEventRepository;

    private final LoggerUtil loggerUtil;

    private final PortalEventsSession session;

    public AdminHewaEventServiceImpl(AdminHewaEventRepository apEventRepository, LoggerUtil loggerUtil, PortalEventsSession session) {
        this.apEventRepository = apEventRepository;
        this.loggerUtil = loggerUtil;
        this.session = session;
    }

    /**
     * @param request: Thông tin cho sự kiện chờ phê duyệt
     * @return Danh sách các sự kiện ở trạng thái chờ phê duyệt
     */
    @Transactional
    @Override
    public PageableObject<AdminHewEventListResponse> getListEventNotApproved(AdminHewEventListRequest request) {
        List<Integer> listStatus= new ArrayList<>();
        if(request.getStatus()!=null){
            for (String item:request.getStatus().split(",") ) {
                try {
                    listStatus.add(Integer.parseInt(item));
                } catch (Exception e){
                    e.printStackTrace(System.out);
                }
            }
        }
        return new PageableObject<AdminHewEventListResponse>(apEventRepository.getEventList(PageRequest.of(request.getPage(),
                request.getSize()), request,session.getData(),listStatus));
    }

    /**
     * @param id: Id sự kiện
     * @return Thông tin chi tiết của sự kiện
     */
    @Transactional
    @Override
    public ResponseObject getDetailEventApproved(String id) {
        //3005 -> không tìm thấy
        return new ResponseObject(apEventRepository.getDetailApprovedById(id).orElseThrow(() -> new RestApiException(Message.APPROVED_DOES_NOT_EXIST)));
    }

    /**
     * @return Danh sách thể loại
     */

    @Override
    public ResponseObject getEventCategory() {
        return new ResponseObject(apEventRepository.getListEventCategory());
    }

    /**
     * @return Danh sách bộ môn
     */

    @Override
    public ResponseObject getEventMajor() {
        return new ResponseObject(apEventRepository.getEventMajor());
    }

    @Override
    public ResponseObject getEventDepartment() {
        return new ResponseObject(apEventRepository.getEventDepartment(session.getData()));
    }

    /**
     * @param request: Thông tin sự kiện
     * @return Page bình luận của sự kiện
     */

    @Override
    public PageableObject<AdminHewCommentEventDetailResponse> getCommentEventById(AdminHewCommentEventDetailRequest request) {
        if (request.getIdEvent() == null) throw new RestApiException("3002");
        return new PageableObject<AdminHewCommentEventDetailResponse>(apEventRepository.getCommentEventById(PageRequest.of(request.getPage(),
                request.getSize()), request.getIdEvent()));
    }

}
