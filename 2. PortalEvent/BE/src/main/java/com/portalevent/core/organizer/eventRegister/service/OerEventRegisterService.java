package com.portalevent.core.organizer.eventRegister.service;

import com.portalevent.core.organizer.eventRegister.model.request.OerCreateCategoryRequest;
import com.portalevent.core.organizer.eventRegister.model.request.OerCreateEventLocationRequest;
import com.portalevent.core.organizer.eventRegister.model.request.OerCreateEventRequest;
import com.portalevent.core.organizer.eventRegister.model.response.OerCategoryResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerDepartmentCampusResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerDepartmentResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerMajorCampusResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerMajorResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerObjectResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerSemesterResponse;
import com.portalevent.entity.Category;
import com.portalevent.entity.Event;
import com.portalevent.entity.EventLocation;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author SonPT
 */
public interface OerEventRegisterService {

    List<OerCategoryResponse> getAll();

    List<OerMajorResponse> getMajors();

    List<OerDepartmentResponse> getDepartments();

    List<OerDepartmentCampusResponse> getDepartmentsByCampus();

    List<OerMajorCampusResponse> getMajorsCampus();

    List<OerSemesterResponse> getSemesters();

    List<OerObjectResponse> getObjects();

    Category create(@Valid OerCreateCategoryRequest request);

    Event create(@Valid OerCreateEventRequest request);

    EventLocation create(@Valid OerCreateEventLocationRequest request);
    void uploadFile(String idTodo, MultipartFile fileBackground, MultipartFile fileThumbnail);

}
