package com.portalevent.core.admin.eventdetailmanagement.repository;

import com.portalevent.core.admin.eventdetailmanagement.model.response.AdminerLocationEventResponse;
import com.portalevent.repository.EventLocationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminerLocationRepository extends EventLocationRepository {

    @Query(value = """
                select el.id as id, el.formality as formality, el.path as path, el.name as name
                from event_location el LEFT JOIN event e ON el.event_id = e.id
                where el.event_id = :idevent
            """, nativeQuery = true)
    List<AdminerLocationEventResponse> getLocationByEventId(@Param("idevent") String idevent);

}
