package com.portalevent.core.approver.eventdetail.repository;

import com.portalevent.core.approver.eventdetail.model.response.AedMajorResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.entity.Major;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AedMajorRepository extends JpaRepository<Major, String> {
    @Query(value = """
                select m.id, m.code, m.name
                from event_major em
                join major m on m.id = em.major_id
                where em.event_id = :idEvent
            """, nativeQuery = true)
    List<AedMajorResponse> getMajorByIdEvent(@Param("idEvent") String id);
}
