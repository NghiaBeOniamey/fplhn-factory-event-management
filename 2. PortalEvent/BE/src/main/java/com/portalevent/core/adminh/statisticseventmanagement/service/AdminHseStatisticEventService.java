package com.portalevent.core.adminh.statisticseventmanagement.service;

import com.portalevent.core.adminh.statisticseventmanagement.model.request.AdminHIdRequest;
import com.portalevent.core.adminh.statisticseventmanagement.model.request.AdminHSemesterMajorRequest;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseCategory;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseEventInMajorResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseEventInSemesterResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseLecturerInEvent;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseListOrganizerResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseDepartmentResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseParticipantInEvent;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseSemesterResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseTopEventResponse;

import java.util.List;

/**
 * @author HoangDV
 */
public interface AdminHseStatisticEventService {

    List<AdminHseSemesterResponse> getAllSemester();

    List<AdminHseDepartmentResponse> getAllDepartment();

    Integer getSumEventBySemester(AdminHSemesterMajorRequest request);

    List<AdminHseEventInSemesterResponse> getEventBySemester(AdminHSemesterMajorRequest request);

    List<AdminHseTopEventResponse> getTopEvent(AdminHSemesterMajorRequest request);

    List<AdminHseListOrganizerResponse> getListOrganizer(AdminHSemesterMajorRequest request);

    List<AdminHseEventInMajorResponse> getEventInMajorByIdSemester(AdminHSemesterMajorRequest request);

    List<AdminHseParticipantInEvent> getListParticipantInEvent(AdminHSemesterMajorRequest request);
    List<AdminHseParticipantInEvent> getListParticipantInEventByCategory(AdminHIdRequest request);
    List<AdminHseLecturerInEvent> getListLecturerInEvent(AdminHSemesterMajorRequest request);

    List<AdminHseCategory> getAllCategory();
}
