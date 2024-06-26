package com.portalevent.core.approver.subjectevent.service;

import com.portalevent.core.approver.subjectevent.model.request.AseSubjectListSubjectRequest;
import com.portalevent.core.approver.subjectevent.model.response.AseSubjectListSubjectResponse;
import com.portalevent.core.common.PageableObject;

public interface AseSubjectEventService {

    PageableObject<AseSubjectListSubjectResponse> getListSubjectByDepartmentCode(AseSubjectListSubjectRequest request);

}
