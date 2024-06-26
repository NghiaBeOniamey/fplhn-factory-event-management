package com.portalevent.core.organizer.importTutorials.repository;

import com.portalevent.entity.EventMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrImportTutorialsEventMajorRepository extends JpaRepository<EventMajor, String> {
    List<EventMajor> findEventMajorByEventId(String eventId);
}
