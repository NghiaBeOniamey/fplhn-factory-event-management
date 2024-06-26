package com.portalevent.core.organizer.importTutorials.repository;

import com.portalevent.entity.EventMajor;
import com.portalevent.entity.EventObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrImportTutorialsEventObjectRepository extends JpaRepository<EventObject, String> {
    List<EventObject> findEventObjectByEventId(String eventId);
}
