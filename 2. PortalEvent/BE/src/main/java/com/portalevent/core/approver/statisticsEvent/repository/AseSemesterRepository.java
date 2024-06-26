package com.portalevent.core.approver.statisticsEvent.repository;

import com.portalevent.core.approver.statisticsEvent.model.response.AseSemesterResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.SemesterRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface AseSemesterRepository extends SemesterRepository {
    @Query(value = """
            SELECT s.id, s.name, s.start_time, s.end_time FROM semester AS s
            """, nativeQuery = true)
    List<AseSemesterResponse> getAllSemester();
}
