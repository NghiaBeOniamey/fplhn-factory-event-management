package com.portalevent.core.approver.subjectevent.service.impl;

import com.portalevent.core.approver.subjectevent.model.request.AseSubjectListSubjectRequest;
import com.portalevent.core.approver.subjectevent.model.response.AseSubjectListSubjectResponse;
import com.portalevent.core.approver.subjectevent.repository.AseSubjectRepository;
import com.portalevent.core.approver.subjectevent.service.AseSubjectEventService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.exeption.rest.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AseSubjectEventServiceImpl implements AseSubjectEventService {

    private final AseSubjectRepository aseSubjectRepository;

    @Override
    public PageableObject<AseSubjectListSubjectResponse> getListSubjectByDepartmentCode(
            AseSubjectListSubjectRequest request
    ) {
        try{
            PageRequest pageRequest = PageRequest.of(request.getPage() - 1,request.getSize());
            return new PageableObject<>(aseSubjectRepository.getListSubjectByDepartmentCode(pageRequest,request));
        }catch(Exception e){
            throw new RestApiException("Không lấy được danh sách chuyên ngành!");
        }
    }

}
