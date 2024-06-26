package com.portalevent.core.organizer.eventRegister.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventRegister.model.response.OerSemesterResponse;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OerSemesterRepository extends SemesterRepository {

    @Query(value = """
        SELECT s.id, s.name, s.start_time, s.end_time, s.start_time_first_block,
        s.end_time_first_block, s.start_time_second_block, s.end_time_second_block
        FROM semester s
        ORDER BY created_date DESC
""", nativeQuery = true)
    List<OerSemesterResponse> getAll();
}
