package com.portalevent.core.organizer.mutipleregister.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrInfomationResponse;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OmrSemesterRepository extends SemesterRepository {

    @Query(value = """
		SELECT id, name, start_time, end_time, start_time_first_block, end_time_first_block, start_time_second_block, end_time_second_block
		FROM semester
		""", nativeQuery = true)
    List<OmrInfomationResponse.OmrSemesterResponse> getAllForDisplay();
}
