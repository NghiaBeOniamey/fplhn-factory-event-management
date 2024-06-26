package com.portalevent.core.approver.statisticsEvent.service;

import com.portalevent.core.approver.statisticsEvent.model.response.AseCategory;
import com.portalevent.core.approver.statisticsEvent.model.response.AseEventInMajorResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseEventInSemesterResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseLecturerInEvent;
import com.portalevent.core.approver.statisticsEvent.model.response.AseListOrganizerResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseMajorResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseParticipantInEvent;
import com.portalevent.core.approver.statisticsEvent.model.response.AseSemesterResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseTopEventResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.infrastructure.session.PortalEventsSession;

import java.util.List;

/**
 * @author HoangDV
 */
public interface AseStatisticEventService {
    List<AseSemesterResponse> getAllSemester();

    Integer getSumEventBySemester(String idSemester);

    List<AseEventInSemesterResponse> getEventBySemester(String idSemester);

    List<AseTopEventResponse> getTopEvent(String idSemester);

    List<AseListOrganizerResponse> getListOrganizer(String idSemester);

    List<AseEventInMajorResponse> getEventInMajorByIdSemester(String idSemester, TokenFindRequest req);

    List<AseParticipantInEvent> getListParticipantInEvent(String idSemester);
    List<AseParticipantInEvent> getListParticipantInEventByCategory(String idSemester, String idCategory);
    List<AseLecturerInEvent> getListLecturerInEvent(String idSemester);

    List<AseCategory> getAllCategory();
}
