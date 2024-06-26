package com.portalevent.core.adminh.eventdetailmanagement.repository;

import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedMajorResponse;
import com.portalevent.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHedMajorRepository extends JpaRepository<Major, String> {

    @Query(value = """
                select m.id, m.code, m.name
                from event_major em
                join major m on m.id = em.major_id
                where em.event_id = :idEvent
            """, nativeQuery = true)
    List<AdminHedMajorResponse> getMajorByIdEvent(@Param("idEvent") String id);

}
