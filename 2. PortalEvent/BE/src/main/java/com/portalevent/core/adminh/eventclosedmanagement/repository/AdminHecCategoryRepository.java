package com.portalevent.core.adminh.eventclosedmanagement.repository;

import com.portalevent.core.adminh.eventclosedmanagement.model.response.AdminHecPropsResponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHecCategoryRepository extends CategoryRepository {

    @Query("""
            SELECT o.id AS id, o.name AS name FROM Category o
            """)
    List<AdminHecPropsResponse> getAllCategory();

}
