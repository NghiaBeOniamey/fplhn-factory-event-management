package com.portalevent.repository;

import com.portalevent.entity.Participant;
import com.portalevent.infrastructure.projection.SimpleEntityProj;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository(ParticipantRepository.NAME)
public interface ParticipantRepository extends JpaRepository<Participant, String> {

    public static final String NAME = "BaseParticipantRepository";

    @Query(value = """
            SELECT id, email FROM participant WHERE email = :email
            """, nativeQuery = true)
    SimpleEntityProj findSimpleEntityByEmail(@Param("email") String email);

    @Query(value = """
            SELECT email FROM participant WHERE event_id = :eventId
            """, nativeQuery = true)
    List<String> getAllEmailByEventId(@Param("eventId") String eventId);

    @Query(value = """
            SELECT id_user FROM participant WHERE event_id = :eventId
            """, nativeQuery = true)
    List<String> getUserIdByEventId(@Param("eventId") String eventId);

}
