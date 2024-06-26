package com.portalevent.core.adminho.statisticseventmanagement.repository;

import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseCategory;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminHOseCategoryRepository extends CategoryRepository {

    @Query(value = """
            SELECT id, name FROM category
            """, nativeQuery = true)
    List<AdminHOseCategory> getAllCategory();

}
