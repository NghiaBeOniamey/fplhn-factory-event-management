package com.portalevent.repository;

import com.portalevent.entity.Event;
import com.portalevent.entity.InvitationTime;
import com.portalevent.infrastructure.backgroundjob.sendinvitationmail.model.EventDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository(EventRepository.NAME)
public interface EventRepository extends JpaRepository<Event, String> {

    public static final String NAME = "BaseEventRepository";

    @Query(value = """
                SELECT a.id, b.name AS category_name, a.name AS event_name, a.description, a.start_time,
                a.end_time, a.background_path, a.banner_path, c.organizer_id, d.id AS location_id, d.path AS location_path,
                d.name AS location_name, d.formality, f.id AS resource_id, f.name AS resource_name, f.path AS resource_path
                FROM event a
                LEFT JOIN category b ON a.category_id = b.id
                LEFT JOIN event_organizer c ON a.id = c.event_id
                LEFT JOIN event_location d ON a.id = d.event_id 
                LEFT JOIN resource f ON a.id = f.event_id
                WHERE a.id = :eventId
            """, nativeQuery = true)
    List<EventDTO> getEventByIdForSendMail(@Param("eventId") String eventId);

    @Query(value = """
		SELECT *
		FROM invitation_ti me a
		WHERE :currentTime > a.time AND a.status = 4 AND a.status = 0
	""", nativeQuery = true)
    List<InvitationTime> getListEventToSendInvitation(@Param("currentTime") Long currentTime);

}
