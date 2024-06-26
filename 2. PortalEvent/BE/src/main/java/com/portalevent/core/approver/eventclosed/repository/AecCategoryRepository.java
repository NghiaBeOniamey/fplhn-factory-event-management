package com.portalevent.core.approver.eventclosed.repository;

import com.portalevent.core.approver.eventclosed.model.response.AecPropsResponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AecCategoryRepository extends CategoryRepository {
    @Query("""
            SELECT o.id AS id, o.name AS name FROM Category o
            """)
    List<AecPropsResponse> getAllCategory();
}
