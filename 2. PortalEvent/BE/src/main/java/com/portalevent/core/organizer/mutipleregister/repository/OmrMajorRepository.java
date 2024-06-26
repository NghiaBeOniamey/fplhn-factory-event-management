package com.portalevent.core.organizer.mutipleregister.repository;

import com.portalevent.core.organizer.mutipleregister.model.response.OmrInfomationResponse;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OmrMajorRepository extends MajorRepository {

    @Query(value = """
			SELECT id, code, name FROM major
	""", nativeQuery = true)
    List<OmrInfomationResponse.OmrMajorResponse> getAllForDisplay();

}
