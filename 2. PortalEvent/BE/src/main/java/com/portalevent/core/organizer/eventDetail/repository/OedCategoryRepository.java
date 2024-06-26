package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.organizer.eventDetail.model.response.OedCategoryResponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */
@Repository
public interface OedCategoryRepository extends CategoryRepository {

    @Query(value = "SELECT id, name FROM category ORDER BY created_date DESC", nativeQuery = true)
    List<OedCategoryResponse> getAll();

    @Query(value = "SELECT id FROM category WHERE code = :code", nativeQuery = true)
    String findByCode(@Param("code") String code);
}
