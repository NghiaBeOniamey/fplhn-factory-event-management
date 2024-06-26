package com.portalevent.core.adminho.eventclosedmanagement.repository;

import com.portalevent.core.adminho.eventclosedmanagement.model.response.AdminHOecPropsResponse;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHOecCategoryRepository extends CategoryRepository {

    @Query("""
            SELECT o.id AS id, o.name AS name FROM Category o
            """)
    List<AdminHOecPropsResponse> getAllCategory();

}
