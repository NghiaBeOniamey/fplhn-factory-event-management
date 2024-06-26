package com.portalevent.core.adminh.statisticseventmanagement.repository;

import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseCategory;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminHseCategoryRepository extends CategoryRepository {

    @Query(value = """
            SELECT id, name FROM category
            """, nativeQuery = true)
    List<AdminHseCategory> getAllCategory();

}
