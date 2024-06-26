package com.portalevent.core.admin.statisticseventmanagement.service;

import com.portalevent.core.admin.statisticseventmanagement.model.request.AdminerIdRequest;
import com.portalevent.core.admin.statisticseventmanagement.model.request.AdminerSemesterMajorRequest;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerCategory;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerEventInMajorResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerEventInSemesterResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerLecturerInEvent;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerListOrganizerResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerDepartmentResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerParticipantInEvent;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerSemesterResponse;
import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerTopEventResponse;

import java.util.List;

/**
 * @author HoangDV
 */
public interface AdminerseStatisticEventService {

    List<AdminerSemesterResponse> getAllSemester();

    List<AdminerDepartmentResponse> getAllDepartment();

    Integer getSumEventBySemester(AdminerSemesterMajorRequest request);

    List<AdminerEventInSemesterResponse> getEventBySemester(AdminerSemesterMajorRequest request);

    List<AdminerTopEventResponse> getTopEvent(AdminerSemesterMajorRequest request);

    List<AdminerListOrganizerResponse> getListOrganizer(AdminerSemesterMajorRequest request);

    List<AdminerEventInMajorResponse> getEventInMajorByIdSemester(AdminerSemesterMajorRequest request);

    List<AdminerParticipantInEvent> getListParticipantInEvent(AdminerSemesterMajorRequest request);
    List<AdminerParticipantInEvent> getListParticipantInEventByCategory(AdminerIdRequest request);
    List<AdminerLecturerInEvent> getListLecturerInEvent(AdminerSemesterMajorRequest request);

    List<AdminerCategory> getAllCategory();

}
