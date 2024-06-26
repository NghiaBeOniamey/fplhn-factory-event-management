package com.portalevent.core.organizer.statisticsEvent.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseSemesterResponse;
import com.portalevent.repository.SemesterRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface OseSemesterRepository extends SemesterRepository {
    @Query(value = """
            SELECT id, name, start_time, end_time FROM semester e
            """, nativeQuery = true)
    List<OseSemesterResponse> getAllSemester();
}
