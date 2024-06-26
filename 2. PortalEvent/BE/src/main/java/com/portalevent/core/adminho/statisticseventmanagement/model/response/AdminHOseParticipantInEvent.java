package com.portalevent.core.adminho.statisticseventmanagement.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author HoangDV
 */
public interface AdminHOseParticipantInEvent {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.expectedParticipants}")
    Integer getExpectedParticipants();

    @Value("#{target.numberParticipantsEnrolled}")
    Integer getNumberParticipantsEnrolled();

    @Value("#{target.numberParticipants}")
    Integer getNumberParticipants();

}
