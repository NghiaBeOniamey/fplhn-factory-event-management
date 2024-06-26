package com.portalevent.core.approver.eventdetail.repository;

import com.portalevent.core.approver.eventdetail.model.response.AedLocationEventResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventLocationRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AedLocationRepository extends EventLocationRepository {

    @Query(value = """
                select el.id as id, el.formality as formality, el.path as path, el.name as name
                from event_location el LEFT JOIN event e ON el.event_id = e.id
                where el.event_id = :idevent
            """, nativeQuery = true)
    List<AedLocationEventResponse> getLocationByEventId(@Param("idevent") String idevent);

}
