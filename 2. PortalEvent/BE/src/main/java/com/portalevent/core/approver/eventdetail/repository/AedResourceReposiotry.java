package com.portalevent.core.approver.eventdetail.repository;

import com.portalevent.core.approver.eventdetail.model.response.AedResourceEventResponce;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.ResourceRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AedResourceReposiotry extends ResourceRepository {
    @Query(value = """
                select r.id as id, r.name as name, r.path as path, r.description as description 
                from resource r 
                where r.event_id = :idevent
            """, nativeQuery = true)
    List<AedResourceEventResponce> getResourcesByEventId(@Param("idevent") String idevent);

}
