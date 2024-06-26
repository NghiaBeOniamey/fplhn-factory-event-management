package com.portalevent.core.approver.eventdetail.repository;

import com.portalevent.core.approver.eventdetail.model.response.AewaEvidenceResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EvidenceRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface AedEvidenceRepository extends EvidenceRepository {
    @Query(value = """
                SELECT v.name, v.path, v.evidence_type, v.description 
                FROM evidence v LEFT JOIN event e ON v.event_id = e.id
                WHERE event_id = :idEvent
            """, nativeQuery = true)
    List<AewaEvidenceResponse> getEvidenceByIdEvent(@Param("idEvent") String id);
}
