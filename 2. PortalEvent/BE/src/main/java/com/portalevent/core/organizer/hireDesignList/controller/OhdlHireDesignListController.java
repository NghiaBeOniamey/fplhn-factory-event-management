package com.portalevent.core.organizer.hireDesignList.controller;

import com.portalevent.core.common.PageableObject;
import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.common.SimpleResponse;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlCreateAndUpdateRequest;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlSendImagesEmailRequestOrganizer;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlSearchEventRequest;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlUpdateImageRequest;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventLocationResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventMajorResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlImageEventResponse;
import com.portalevent.core.organizer.hireDesignList.service.OhdlHireDesignListService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ADMINISTRATIVE_HIRE_DESIGN_LIST)
public class OhdlHireDesignListController {

    private final OhdlHireDesignListService service;

    public OhdlHireDesignListController(OhdlHireDesignListService service) {
        this.service = service;
    }

    @PostMapping("/send-mail-images")
    private void sendMailImages(@RequestBody OhdlSendImagesEmailRequestOrganizer emails) {
        service.sendEmailImageEventHireDesign(emails);
    }

    //Hiển thị list hire design và tìm kiếm
    @PostMapping("/get-list-hire-design")
    public PageableObject searchHireDesign(@RequestBody OhdlSearchEventRequest request) {
        return service.getAllHireDesignList(request);
    }

    //hiển thị list organizer
    @GetMapping("/list-organizer-hire-design")
    public List<SimpleResponse> listOrganizerOfHireDesign() {
        return service.listOrganizerOfHireDesignList();
    }

    //Hiển thị list major
    @GetMapping("/list-major-hire-design")
    public List<OhdlEventMajorResponse> listMajorOfHireDesign() {
        return service.listMajorOfHireDesignList();
    }

    //Hiển thị list formality
    @GetMapping("/list-formality-hire-design")
    public List<OhdlEventLocationResponse> listFomarlityOfHireDesign() {
        return service.listFormalityOfHireDesign();
    }

    //Hiển thị 1 hire design trong list hire design
    @GetMapping("/get-hire-design-by-id/{id}")
    public OhdlEventResponse getHireDesignById(@PathVariable("id") String id) {
        return service.getOneHireDesign(id);
    }

    //Hiển thị 1 location
    @GetMapping("/list-location-by-id/{id}")
    public List<OhdlEventLocationResponse> listLocationById(@PathVariable("id") String id) {
        return service.listLocationOfHireDesign(id);
    }

    //lấy ra ảnh theo hire design
    @GetMapping("/get-image-by-id/{id}")
    public OhdlImageEventResponse getImageById(@PathVariable("id") String id) {
        return service.getImageById(id);
    }

    //Update ảnh của hire design
    @PostMapping("/upload-image")
    public ResponseObject uploadImage(@RequestParam("file") MultipartFile file,
                                      @RequestParam("type") int type,
                                      String idEvent, OhdlUpdateImageRequest request) {
        return new ResponseObject(service.uploadImage(file, type, idEvent, request));
    }

    //Delete ảnh (Chưa dùng)
    @DeleteMapping("/delete-image/{id}")
    public ResponseObject delete(@PathVariable("id") String id) {
        return new ResponseObject(service.delete(id));
    }

    //Thêm địa điểm
    @PostMapping("/add-location-hire-design")
    public ResponseObject addLocationHireDesign(@RequestBody OhdlCreateAndUpdateRequest request) {
        return new ResponseObject(service.createLocation(request));
    }

    //Cập nhật địa điểm
    @PutMapping("/update-location-hire-design/{id}")
    public ResponseObject updateLocationHireDesign(@PathVariable("id") String id, @RequestBody OhdlCreateAndUpdateRequest request) {
        return new ResponseObject(service.updateLocation(id, request));
    }

    //Xóa địa điểm
    @DeleteMapping("/delete-location-hire-design/{id}")
    public ResponseObject deleteLocation(@PathVariable("id") String id, @RequestParam(required = false) String idEvent) {
        return new ResponseObject(service.deleteLocation(id, idEvent));
    }
}
