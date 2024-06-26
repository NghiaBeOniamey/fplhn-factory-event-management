package com.portalevent.core.adminho.eventdetailmanagement.repository;

import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOedResourceEventResponse;
import com.portalevent.repository.ResourceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHOedResourceRepository extends ResourceRepository {

    @Query(value = """
                select r.id as id, r.name as name, r.path as path, r.description as description 
                from resource r 
                where r.event_id = :idevent
            """, nativeQuery = true)
    List<AdminHOedResourceEventResponse> getResourcesByEventId(@Param("idevent") String idevent);

}
