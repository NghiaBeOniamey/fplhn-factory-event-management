package com.portalevent.core.organizer.statisticsEvent.service.impl;

import com.portalevent.core.organizer.statisticsEvent.model.response.OseEventInSemesterResponse;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseParticipationEventsResponse;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseSemesterResponse;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseTopEventResponse;
import com.portalevent.core.organizer.statisticsEvent.repository.OseEventOrganizerRepository;
import com.portalevent.core.organizer.statisticsEvent.repository.OseEventRepository;
import com.portalevent.core.organizer.statisticsEvent.repository.OseSemesterRepository;
import com.portalevent.core.organizer.statisticsEvent.service.OseStatisticsEventService;
import com.portalevent.infrastructure.session.PortalEventsSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author HoangDV
 */
@Service
public class OseStatisticsEventServiceImpl implements OseStatisticsEventService {

    private final OseSemesterRepository semesterRepository;

    private final OseEventOrganizerRepository eventOrganizerRepository;

    private final OseEventRepository eventRepository;

    private final PortalEventsSession session;

    public OseStatisticsEventServiceImpl(OseSemesterRepository semesterRepository, OseEventOrganizerRepository eventOrganizerRepository, OseEventRepository eventRepository, PortalEventsSession session) {
        this.semesterRepository = semesterRepository;
        this.eventOrganizerRepository = eventOrganizerRepository;
        this.eventRepository = eventRepository;
        this.session = session;
    }

    /**
     * @return List semester
     * @description Lấy ra tất cả học kỳ
     */
    @Override
    public List<OseSemesterResponse> getAllSemester() {
        return semesterRepository.getAllSemester();
    }

    /**
     * @param idSemester: Id học kỳ
     * @return Tổng số sự kiện đã tổ chưc trong kỳ, tổng số sự kiện là cohost và host
     */
    @Override
    public OseParticipationEventsResponse getRoleUserInEventInSemester(String idSemester) {
        List<Integer> listRole = eventOrganizerRepository.getRoleUserInEventInSemester(idSemester, session.getCurrentUserCode(), session.getData());
        Long numberCoHost = 0L;
        Long numberHost = 0L;
        OseParticipationEventsResponse response = new OseParticipationEventsResponse();
        for (Integer role : listRole) {
            if (role == 0) {
                numberHost++;
            } else {
                numberCoHost++;
            }
        }
        response.setTotalEvents(Long.valueOf(listRole.size()));
        response.setNumberOfHost(numberCoHost);
        response.setNumberOfCoHost(numberHost);
        return response;
    }

    /**
     * @param idSemester: Id của học kỳ
     * @return Thông tin của top 3 sự kiện có nhiều người tham gia nhất
     */
    @Override
    public List<OseTopEventResponse> getTopEvent(String idSemester) {
        return eventRepository.getTopEvent(idSemester, session.getCurrentUserCode());
    }

    /**
     * @param idSemester: Id của học kỳ
     * @return Trạng thái của các sự kiện và số lượng tương ứng
     * @description Ví dụ lấy ra trạng thái 0 là đã đóng với số lượng 2
     */
    @Override
    public List<OseEventInSemesterResponse> getEventBySemesterAndOrganizer(String idSemester) {
        return eventRepository.getEventBySemesterAndOrganizer(session.getCurrentUserCode(), idSemester);
    }

    /**
     * @param idSemester: Id của học kỳ
     * @return Tổng số sự kiện đã đăng ký trong kỳ ở tất cả các trạng thái
     */
    @Override
    public Integer getSumEventBySemesterAndOrganizer(String idSemester) {
        return eventRepository.getSumEventBySemesterAndOrganizer(session.getCurrentUserCode(), idSemester);
    }
}
