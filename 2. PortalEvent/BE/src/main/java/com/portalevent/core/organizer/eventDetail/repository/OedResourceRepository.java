package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedResourceResponse;
import com.portalevent.repository.ResourceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface    OedResourceRepository extends ResourceRepository {

    @Query(value = """
            SELECT a.id, a.name, a.path, a.description 
            FROM resource a 
            WHERE a.event_id = :idEvent
             ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    List<OedResourceResponse> getAllResource(@Param("idEvent") String idEvent );
}
