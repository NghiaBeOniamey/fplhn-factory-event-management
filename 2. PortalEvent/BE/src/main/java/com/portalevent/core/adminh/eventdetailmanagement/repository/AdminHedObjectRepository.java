package com.portalevent.core.adminh.eventdetailmanagement.repository;

import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedObjectEventResponse;
import com.portalevent.repository.EventObjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHedObjectRepository extends EventObjectRepository {

    @Query(value = """
                select o.id as id, o.name as name
                from event_object eo 
                join object o on o.id = eo.object_id
                where eo.event_id = :idevent
            """, nativeQuery = true)
    List<AdminHedObjectEventResponse> getObjectByEventId(@Param("idevent") String idevent);

}
