package com.portalevent.core.adminho.statisticseventmanagement.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author HoangDV
 */
public interface AdminHOseLecturerInEvent {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.numberParticipantsEnrolled}")
    Integer getNumberParticipantsEnrolled();

    @Value("#{target.numberParticipants}")
    Integer getNumberParticipants();

}
