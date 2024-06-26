package com.portalevent.core.organizer.mutipleregister.repository;

import com.portalevent.core.organizer.mutipleregister.model.response.OmrInfomationResponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OmrCategoryRepository extends CategoryRepository {

    @Query(value = """
			SELECT id, name FROM category
	""", nativeQuery = true)
    List<OmrInfomationResponse.OmrCategoryResponse> getAllForDisplay();

}
