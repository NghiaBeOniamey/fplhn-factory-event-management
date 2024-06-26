package com.portalevent.core.organizer.eventRegistered.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventRegistered.model.response.OerdCategoryResponse;
import com.portalevent.core.organizer.eventRegistered.model.response.OerdEventOrganizerResponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OerdEventOrganizerRepository extends CategoryRepository {
    @Query(value = """
                SELECT eo.organizer_id as 'id_organizer' FROM event_organizer eo
                        WHERE eo.event_id = :#{#event_id}
                        GROUP BY eo.organizer_id
            """, nativeQuery = true)
    List<OerdEventOrganizerResponse> getListOrganizerId(@Param("event_id") String eventId );
}
