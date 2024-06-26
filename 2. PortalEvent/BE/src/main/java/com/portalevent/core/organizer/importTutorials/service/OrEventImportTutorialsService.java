package com.portalevent.core.organizer.importTutorials.service;

import com.portalevent.core.organizer.importTutorials.model.request.OrEventImportTutorialsFilterRequest;
import com.portalevent.core.organizer.importTutorials.model.request.OrEventImportTutorialsRequest;
import com.portalevent.core.organizer.importTutorials.model.request.OrEventListDataImportTutorialsRequest;
import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsResponseDTO;
import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsSemesterResponse;
import com.portalevent.entity.Event;

import java.util.HashMap;
import java.util.List;

public interface OrEventImportTutorialsService {
    List<OrImportTutorialsSemesterResponse> getAllSemester();
    List<Event> importTutotials(OrEventImportTutorialsRequest request);
    List<OrImportTutorialsResponseDTO> getAllTutorials(OrEventImportTutorialsFilterRequest request);
}