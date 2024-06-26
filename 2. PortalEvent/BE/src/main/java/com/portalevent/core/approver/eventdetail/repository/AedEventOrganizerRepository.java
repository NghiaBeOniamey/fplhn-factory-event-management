package com.portalevent.core.approver.eventdetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.entity.EventOrganizer;
import com.portalevent.repository.EventOrganizerRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AedEventOrganizerRepository extends EventOrganizerRepository {
    @Query(value = """
                SELECT o.organizer_id
                FROM event_organizer o
                LEFT JOIN event e ON o.event_id = e.id
                WHERE event_id = :idEvent
            """, nativeQuery = true)
    List<String> getIdOrganizerByIdEvent(@Param("idEvent") String id);

    List<EventOrganizer> findAllByEventId(String eventId);
}
