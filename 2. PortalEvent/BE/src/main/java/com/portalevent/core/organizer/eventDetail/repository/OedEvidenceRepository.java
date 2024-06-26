package com.portalevent.core.organizer.eventDetail.repository;


import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventEvidenceResponse;
import com.portalevent.repository.EvidenceRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OedEvidenceRepository extends EvidenceRepository {
    @Query(value = """
            SELECT a.id, a.event_id as eventId,
            		a.name, a.path, a.description,
            		a.evidence_type as evidenceType
            FROM evidence a 
            WHERE a.event_id = :idEvent 
            ORDER BY a.created_date;
            """, nativeQuery = true)
    List<OedEventEvidenceResponse> getAllEventEvidence(@Param("idEvent") String idEvent );
}
