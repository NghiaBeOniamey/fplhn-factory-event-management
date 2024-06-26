package com.portalevent.core.organizer.eventDetail.model.response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * @author HoangDV
 * @description
 * @update
 * @since 25/07/2023 17:48
 */

//Sửa. Lấy bằng mockAPI
public interface OedOrganizerResponse {
    @Value("#{target.record_number}")
    int getRecordNumber();

    @Value("#{target.id}")
    String getId();

    @Value("#{target.name}")
    String getName();

    @Value("#{target.phone_number}")
    String getPhoneNumber();

    @Value("#{target.email}")
    String getEmail();

}
