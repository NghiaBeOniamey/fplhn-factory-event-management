package com.portalevent.core.adminh.eventwaitingapprovalmanagement.model.respone;

import org.springframework.beans.factory.annotation.Value;

public interface AdminerListDepartmentResponse {

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

}
