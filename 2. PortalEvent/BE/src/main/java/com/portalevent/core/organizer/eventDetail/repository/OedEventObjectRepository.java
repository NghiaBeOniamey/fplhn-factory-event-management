package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventObjectRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author HoangDV
 */
@Repository
public interface OedEventObjectRepository extends EventObjectRepository {
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM event_object a 
        WHERE a.event_id = :idEvent 
    """, nativeQuery = true)
    void deleteEventMajorByIdEvent(@Param("idEvent") String id );
}
