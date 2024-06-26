package com.portalevent.core.approver.eventdetail.repository;

import com.portalevent.core.approver.eventdetail.model.response.AedObjectEventResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventObjectRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AedObjectRepository extends EventObjectRepository {
    @Query(value = """
                select o.id as id, o.name as name
                from event_object eo 
                join object o on o.id = eo.object_id
                where eo.event_id = :idevent
            """, nativeQuery = true)
    List<AedObjectEventResponse> getObjectByEventId(@Param("idevent") String idevent);
}
