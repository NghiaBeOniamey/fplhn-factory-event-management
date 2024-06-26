package com.portalevent.core.approver.subjectevent.model.request;

import com.portalevent.core.common.PageableRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AseSubjectListSubjectRequest extends PageableRequest {

    private String name;

    private String code;

    private String departmentCode;

}
