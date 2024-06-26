package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.organizer.eventDetail.model.response.OedObjectResponse;
import com.portalevent.repository.EventObjectRepository;
import com.portalevent.repository.ObjectRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface OedObjectRepository extends ObjectRepository {
    @Query(value = """
            SELECT id, name FROM object
            ORDER BY name ASC 
            """, nativeQuery = true)
    List<OedObjectResponse> getAll();

    @Query(value = """
            SELECT o.id, name FROM object AS o
            JOIN event_object AS eo
            ON eo.object_id = o.id
            WHERE eo.event_id = :idEvent
            ORDER BY name ASC 
            """, nativeQuery = true)
    List<OedObjectResponse> getObjectByIdEvent(@Param("idEvent") String id);
}
