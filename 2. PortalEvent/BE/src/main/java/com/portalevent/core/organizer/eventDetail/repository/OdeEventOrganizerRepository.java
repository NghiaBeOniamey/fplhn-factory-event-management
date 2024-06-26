package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventOrganizerResponse;
import com.portalevent.entity.EventOrganizer;
import com.portalevent.infrastructure.constant.EventRole;
import com.portalevent.repository.EventOrganizerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author thangncph26123
 */
@Repository
public interface OdeEventOrganizerRepository extends EventOrganizerRepository {

    @Query(value = """
            SELECT a.id, a.event_id, a.organizer_code, a.event_role, a.organizer_id, a.meeting_hour
            FROM event_organizer a 
            LEFT JOIN event e on a.event_id = e.id
            WHERE a.event_id = :idEvent
            ORDER BY a.event_role
            """, nativeQuery = true)
    List<OedEventOrganizerResponse> getAllEventOrganizer(@Param("idEvent") String idEvent);

    Optional<EventOrganizer> findByOrganizerIdAndEventId(String idOrganizer, String idEvent);

    List<EventOrganizer> findByEventIdAndEventRole(String eventId, EventRole eventRole);
}
