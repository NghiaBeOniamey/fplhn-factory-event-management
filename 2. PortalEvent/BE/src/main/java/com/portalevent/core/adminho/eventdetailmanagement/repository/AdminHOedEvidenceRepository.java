package com.portalevent.core.adminho.eventdetailmanagement.repository;

import com.portalevent.core.adminho.eventdetailmanagement.model.response.AdminHOewEvidenceResponse;
import com.portalevent.repository.EvidenceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminHOedEvidenceRepository extends EvidenceRepository {

    @Query(value = """
                SELECT v.name, v.path, v.evidence_type, v.description 
                FROM evidence v LEFT JOIN event e ON v.event_id = e.id
                WHERE event_id = :idEvent
            """, nativeQuery = true)
    List<AdminHOewEvidenceResponse> getEvidenceByIdEvent(@Param("idEvent") String id);

}
