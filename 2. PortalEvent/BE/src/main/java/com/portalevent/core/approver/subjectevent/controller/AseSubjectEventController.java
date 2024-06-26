package com.portalevent.core.approver.subjectevent.controller;

import com.portalevent.core.approver.subjectevent.model.request.AseSubjectListSubjectRequest;
import com.portalevent.core.approver.subjectevent.model.response.AseSubjectListSubjectResponse;
import com.portalevent.core.approver.subjectevent.service.AseSubjectEventService;
import com.portalevent.core.common.PageableObject;
import com.portalevent.infrastructure.constant.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping(Constants.UrlPath.URL_APO_APPROVER_SUBJECT_EVENT)
public class AseSubjectEventController {

    private final AseSubjectEventService aseSubjectEventService;

    @GetMapping("/get-list-subject")
    public PageableObject<AseSubjectListSubjectResponse> getListSubject(AseSubjectListSubjectRequest request){
        return aseSubjectEventService.getListSubjectByDepartmentCode(request);
    }

}
