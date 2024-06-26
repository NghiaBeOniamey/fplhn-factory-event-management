package com.portalevent.core.organizer.importTutorials.repository;

import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsSemesterResponse;
import com.portalevent.entity.EventLocation;
import com.portalevent.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrImportTutorialsEventLocationRepository extends JpaRepository<EventLocation, String> {
    List<EventLocation> findEventLocationByEventId(String eventId);
}
