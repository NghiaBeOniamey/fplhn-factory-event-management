package com.portalevent.core.organizer.statisticsEvent.service;

import com.portalevent.core.organizer.statisticsEvent.model.response.OseEventInSemesterResponse;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseParticipationEventsResponse;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseSemesterResponse;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseTopEventResponse;
import java.util.List;

/**
 * @author HoangDV
 */
public interface OseStatisticsEventService {
    List<OseSemesterResponse> getAllSemester();

    OseParticipationEventsResponse getRoleUserInEventInSemester(String idSemester);

    List<OseTopEventResponse> getTopEvent(String idSemester);

    List<OseEventInSemesterResponse> getEventBySemesterAndOrganizer(String idSemester);

    Integer getSumEventBySemesterAndOrganizer(String idSemester);
}
