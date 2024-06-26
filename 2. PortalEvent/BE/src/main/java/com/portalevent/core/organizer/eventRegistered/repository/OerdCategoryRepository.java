package com.portalevent.core.organizer.eventRegistered.repository;

import com.portalevent.core.organizer.eventRegistered.model.response.OerdCategoryResponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OerdCategoryRepository extends CategoryRepository {

    @Query(value = "SELECT id, name FROM category ORDER BY name DESC", nativeQuery = true)
    List<OerdCategoryResponse> getAll();

}
