package com.portalevent.core.adminho.statisticseventmanagement.service;

import com.portalevent.core.adminho.statisticseventmanagement.model.request.AdminHOIdRequest;
import com.portalevent.core.adminho.statisticseventmanagement.model.request.AdminHOSCDRequest;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseCampusResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseCategory;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseDepartmentResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseEventInMajorResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseEventInSemesterResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseLecturerInEvent;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseListOrganizerResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseMajorResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseParticipantInEvent;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseSemesterResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseTopEventResponse;

import java.util.List;

/**
 * @author HoangDV
 */
public interface AdminHOseStatisticEventService {

    List<AdminHOseSemesterResponse> getAllSemester();

    List<AdminHOseCampusResponse> getAllCampus();

    List<AdminHOseDepartmentResponse> getDepartmentByCampusId(Long campusId);

    Integer getSumEventBySemester(AdminHOSCDRequest request);

    List<AdminHOseEventInSemesterResponse> getEventBySemester(AdminHOSCDRequest request);

    List<AdminHOseTopEventResponse> getTopEvent(AdminHOSCDRequest request);

    List<AdminHOseListOrganizerResponse> getListOrganizer(AdminHOSCDRequest request);

    List<AdminHOseEventInMajorResponse> getEventInMajorByIdSemester(AdminHOSCDRequest request);

    List<AdminHOseParticipantInEvent> getListParticipantInEvent(AdminHOSCDRequest request);

    List<AdminHOseParticipantInEvent> getListParticipantInEventByCategory(AdminHOIdRequest request);

    List<AdminHOseLecturerInEvent> getListLecturerInEvent(AdminHOSCDRequest request);

    List<AdminHOseCategory> getAllCategory();
}
