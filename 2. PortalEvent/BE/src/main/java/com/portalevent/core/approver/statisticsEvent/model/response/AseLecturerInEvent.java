package com.portalevent.core.approver.statisticsEvent.model.response;

import org.springframework.beans.factory.annotation.Value;

/**
 * @author HoangDV
 */
public interface AseLecturerInEvent {

    @Value("#{target.name}")
    String getName();

    @Value("#{target.numberParticipantsEnrolled}")
    Integer getNumberParticipantsEnrolled();

    @Value("#{target.numberParticipants}")
    Integer getNumberParticipants();

}
