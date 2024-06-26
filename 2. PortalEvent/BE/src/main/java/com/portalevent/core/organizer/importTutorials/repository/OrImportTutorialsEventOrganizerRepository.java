package com.portalevent.core.organizer.importTutorials.repository;

import com.portalevent.entity.Event;
import com.portalevent.entity.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrImportTutorialsEventOrganizerRepository extends JpaRepository<EventOrganizer, String> {

}
