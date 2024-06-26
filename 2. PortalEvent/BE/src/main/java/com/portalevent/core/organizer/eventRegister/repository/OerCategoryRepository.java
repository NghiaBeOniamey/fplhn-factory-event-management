package com.portalevent.core.organizer.eventRegister.repository;

import com.portalevent.core.organizer.eventRegister.model.response.OerCategoryResponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OerCategoryRepository extends CategoryRepository {

    @Query(value = "SELECT id, name FROM category ORDER BY created_date DESC", nativeQuery = true)
    List<OerCategoryResponse> getAll();

    @Query(value = "SELECT id FROM category WHERE name = :name", nativeQuery = true)
    String findByCode(@Param("name") String code);
}
