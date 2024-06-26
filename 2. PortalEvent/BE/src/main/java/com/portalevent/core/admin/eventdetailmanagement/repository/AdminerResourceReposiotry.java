package com.portalevent.core.admin.eventdetailmanagement.repository;

import com.portalevent.core.admin.eventdetailmanagement.model.response.AdminerResourceEventResponce;
import com.portalevent.repository.ResourceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminerResourceReposiotry extends ResourceRepository {

    @Query(value = """
                select r.id as id, r.name as name, r.path as path, r.description as description 
                from resource r 
                where r.event_id = :idevent
            """, nativeQuery = true)
    List<AdminerResourceEventResponce> getResourcesByEventId(@Param("idevent") String idevent);

}
