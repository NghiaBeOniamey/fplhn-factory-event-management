package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedAgendaItemResponse;
import com.portalevent.infrastructure.session.PortalEventsSession;
import com.portalevent.repository.AgendaItemRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository
public interface OdeAgendaItemRepository extends AgendaItemRepository {

    @Query(value = """
            SELECT a.id, a.event_id, a.organizer_id, a.start_time, a.end_time, a.description
            FROM agenda_item a  
            WHERE a.event_id = :idEvent
            ORDER BY a.created_date ASC
            """, nativeQuery = true)
    List<OedAgendaItemResponse> getAllAgendaItems(@Param("idEvent") String idEvent);

    @Query(value = """
            SELECT a.id
            FROM agenda_item a  
            WHERE a.event_id = :idEvent AND a.organizer_id = :organizerId 
            """, nativeQuery = true)
    List<String> checkAgendaOrganizer(@Param("idEvent") String idEvent, @Param("organizerId") String organizerId );
}
