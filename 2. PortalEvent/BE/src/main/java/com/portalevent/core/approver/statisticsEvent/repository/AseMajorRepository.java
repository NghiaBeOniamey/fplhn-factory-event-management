package com.portalevent.core.approver.statisticsEvent.repository;

import com.portalevent.core.approver.statisticsEvent.model.response.AseMajorResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.MajorRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface AseMajorRepository extends MajorRepository {
    @Query(value = """
        SELECT DISTINCT m.id, m.code, m.name FROM major_campus mc
        LEFT JOIN department_campus dc ON mc.department_campus_id = dc.department_campus_id
        LEFT JOIN department d ON d.department_id = dc.department_id
        LEFT JOIN campus c ON c.campus_id = dc.campus_id
        LEFT JOIN major m ON m.major_id = mc.major_id
        WHERE
            :#{#req.currentTrainingFacilityCode} IS NULL OR
            :#{#req.currentTrainingFacilityCode} LIKE '' OR
            c.code = :#{#req.currentTrainingFacilityCode}
        AND
            :#{#req.currentSubjectCode} IS NULL OR
            :#{#req.currentSubjectCode} LIKE '' OR
            d.code = :#{#req.currentSubjectCode}
    """, nativeQuery = true)
    List<AseMajorResponse> getAllMajor(TokenFindRequest req);
}
