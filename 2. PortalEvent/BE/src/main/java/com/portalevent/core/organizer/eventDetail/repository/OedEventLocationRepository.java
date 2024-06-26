package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventLocationResponse;
import com.portalevent.repository.EventLocationRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HoangDV
 */
@Repository
public interface OedEventLocationRepository extends EventLocationRepository {
    @Query(value = """
        SELECT * FROM event_location AS el  
        WHERE el.event_id = :idEvent 
        """, nativeQuery = true)
    List<OedEventLocationResponse> getEventLocationByIdEvent(@Param("idEvent") String id );
    @Transactional
    @Modifying
    @Query(value = """
        DELETE FROM event_location  
        WHERE event_location.id = :idEvent 
        """, nativeQuery = true)
    void deleteEventLocationByIdEvent(@Param("idEvent") String id);
}
