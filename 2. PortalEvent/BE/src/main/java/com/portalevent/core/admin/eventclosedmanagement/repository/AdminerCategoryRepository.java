package com.portalevent.core.admin.eventclosedmanagement.repository;

import com.portalevent.core.admin.eventclosedmanagement.model.response.AdminerPropsResponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminerCategoryRepository extends CategoryRepository {
    @Query("""
            SELECT o.id AS id, o.name AS name FROM Category o
            """)
    List<AdminerPropsResponse> getAllCategory();

}
