package com.portalevent.core.admin.eventdetailmanagement.repository;

import com.portalevent.core.admin.eventdetailmanagement.model.response.AdminerObjectEventResponse;
import com.portalevent.repository.EventObjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminerEDObjectRepository extends EventObjectRepository {
    @Query(value = """
                select o.id as id, o.name as name
                from event_object eo 
                join object o on o.id = eo.object_id
                where eo.event_id = :idevent
            """, nativeQuery = true)
    List<AdminerObjectEventResponse> getObjectByEventId(@Param("idevent") String idevent);

}
