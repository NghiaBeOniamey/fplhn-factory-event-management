package com.portalevent.core.organizer.importTutorials.controller;

import com.portalevent.core.common.ResponseObject;
import com.portalevent.core.organizer.importTutorials.model.request.OrEventImportTutorialsFilterRequest;
import com.portalevent.core.organizer.importTutorials.model.request.OrEventImportTutorialsRequest;
import com.portalevent.core.organizer.importTutorials.service.OrEventImportTutorialsService;
import com.portalevent.infrastructure.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.UrlPath.URL_API_ORGANIZER_IMPORT_TUTORIAL)

public class OrEventImportToturialsController {

    private final OrEventImportTutorialsService service;

    public OrEventImportToturialsController(OrEventImportTutorialsService service) {
        this.service = service;
    }

    @GetMapping("/get-all-semester")
    public ResponseObject getAllSemester(){
        return new ResponseObject(service.getAllSemester());
    }

    @PostMapping()
    public ResponseObject importTutorials(@RequestBody OrEventImportTutorialsRequest request){
        return new ResponseObject(service.importTutotials(request));
    }


    @GetMapping("/get-all-tutorials")
        public ResponseObject getAll(final OrEventImportTutorialsFilterRequest request){
        return new ResponseObject(service.getAllTutorials(request));
    }
}
