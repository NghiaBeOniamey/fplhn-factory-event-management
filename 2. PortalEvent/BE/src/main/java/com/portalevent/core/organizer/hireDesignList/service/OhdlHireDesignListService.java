package com.portalevent.core.organizer.hireDesignList.service;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlCreateAndUpdateRequest;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlSearchEventRequest;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlSendImagesEmailRequestOrganizer;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlUpdateImageRequest;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlCustomEventResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventLocationResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventMajorResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlImageEventResponse;
import com.portalevent.entity.Event;
import com.portalevent.entity.EventLocation;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OhdlHireDesignListService {

    //Hiển thị list Hire Design và Search
    PageableObject<OhdlCustomEventResponse> getAllHireDesignList(OhdlSearchEventRequest request);

    //Lấy ra 1 hire design trong list hire design
    OhdlEventResponse getOneHireDesign(String id);

    //Hiển thị list organizer
    List<SimpleResponse> listOrganizerOfHireDesignList();

    //Hiển thị list major
    List<OhdlEventMajorResponse> listMajorOfHireDesignList();

    //Hiển thị list formality
    List<OhdlEventLocationResponse> listFormalityOfHireDesign();

    //Hiển thị list location
    List<OhdlEventLocationResponse> listLocationOfHireDesign(String id);

    //Cập nhật ảnh
    Event uploadImage(MultipartFile file, int type, String id, OhdlUpdateImageRequest request);

    //Hiển thị ảnh trong hire design
    OhdlImageEventResponse getImageById(String id);

    //Delete images
    boolean delete(String id);

    //Add location
    EventLocation createLocation(@Valid OhdlCreateAndUpdateRequest request);

    //Update location
    EventLocation updateLocation(String id, @Valid OhdlCreateAndUpdateRequest request);

    //delete Location
    Boolean deleteLocation(String id, String idEvent);

    void sendEmailImageEventHireDesign(OhdlSendImagesEmailRequestOrganizer requestOrganizer);


}
