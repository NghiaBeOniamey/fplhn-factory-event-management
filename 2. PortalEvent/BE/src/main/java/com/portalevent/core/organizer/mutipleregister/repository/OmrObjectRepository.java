package com.portalevent.core.organizer.mutipleregister.repository;

import com.portalevent.core.organizer.mutipleregister.model.response.OmrInfomationResponse;
import com.portalevent.repository.ObjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OmrObjectRepository extends ObjectRepository {

    @Query(value = """
			SELECT id, name FROM object ORDER BY created_date 
	""", nativeQuery = true)
    List<OmrInfomationResponse.OmrObjectResponse> getAllForDisplay();

}
