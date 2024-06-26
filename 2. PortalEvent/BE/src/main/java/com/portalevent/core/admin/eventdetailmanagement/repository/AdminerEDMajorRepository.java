package com.portalevent.core.admin.eventdetailmanagement.repository;

import com.portalevent.core.admin.eventdetailmanagement.model.response.AdminerMajorResponse;
import com.portalevent.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminerEDMajorRepository extends JpaRepository<Major, String> {
    @Query(value = """
                select m.id, m.code, m.name
                from event_major em
                join major m on m.id = em.major_id
                where em.event_id = :idEvent
            """, nativeQuery = true)
    List<AdminerMajorResponse> getMajorByIdEvent(@Param("idEvent") String id);

}
