package com.portalevent.core.admin.eventclosedmanagement.model.response;

import org.springframework.beans.factory.annotation.Value;

public interface AdminerEventCloseResponse {
    @Value("#{target.stt}")
    Integer getStt();

    String getId();

    String getName();

    String getSemester();

    String getBlockNumber();

    String getCategory();

    String getObject();

    String getMajor();

    String getBanner();

    String getReason();
}
