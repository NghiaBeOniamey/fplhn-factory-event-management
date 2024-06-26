package com.portalevent.core.admin.eventdetailmanagement.repository;

import com.portalevent.entity.EventOrganizer;
import com.portalevent.repository.EventOrganizerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminerEventOrganizerRepository extends EventOrganizerRepository {

    @Query(value = """
                SELECT o.organizer_id
                FROM event_organizer o
                LEFT JOIN event e ON o.event_id = e.id
                WHERE event_id = :idEvent
            """, nativeQuery = true)
    List<String> getIdOrganizerByIdEvent(@Param("idEvent") String id);

    List<EventOrganizer> findAllByEventId(String eventId);

}
