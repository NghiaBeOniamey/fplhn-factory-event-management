package com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.impl;

import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOewCommentEventDetailRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.request.AdminHOewEventListRequest;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOewCommentEventDetailResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.model.respone.AdminHOewEventListResponse;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.repository.AdminHOewaEventRepository;
import com.portalevent.core.adminho.eventwaitingapprovalmanagement.service.AdminHOewEventService;
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
public class AdminHOewaEventServiceImpl implements AdminHOewEventService {

    private final AdminHOewaEventRepository apEventRepository;

    public AdminHOewaEventServiceImpl(AdminHOewaEventRepository apEventRepository) {
        this.apEventRepository = apEventRepository;
    }

    /**
     * @param request: Thông tin cho sự kiện chờ phê duyệt
     * @return Danh sách các sự kiện ở trạng thái chờ phê duyệt
     */
    @Transactional
    @Override
    public PageableObject<AdminHOewEventListResponse> getListEventNotApproved(AdminHOewEventListRequest request) {
        List<Integer> listStatus = new ArrayList<>();
        if (request.getStatus() != null) {
            for (String item : request.getStatus().split(",")) {
                try {
                    listStatus.add(Integer.parseInt(item));
                } catch (Exception e) {
                    e.printStackTrace(System.out);
                }
            }
        }
        return new PageableObject<AdminHOewEventListResponse>(apEventRepository.getEventList(PageRequest.of(request.getPage(),
                request.getSize()), request, listStatus));
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

    /**
     * @param request: Thông tin sự kiện
     * @return Page bình luận của sự kiện
     */

    @Override
    public PageableObject<AdminHOewCommentEventDetailResponse> getCommentEventById(AdminHOewCommentEventDetailRequest request) {
        if (request.getIdEvent() == null) throw new RestApiException("3002");
        return new PageableObject<AdminHOewCommentEventDetailResponse>(apEventRepository.getCommentEventById(PageRequest.of(request.getPage(),
                request.getSize()), request.getIdEvent()));
    }

}
