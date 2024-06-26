package com.portalevent.core.admin.statisticseventmanagement.repository;

import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerCategory;
import com.portalevent.repository.CategoryRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminerseCategoryRepository extends CategoryRepository {

    @Query(value = """
            SELECT id, name FROM category
            """, nativeQuery = true)
    List<AdminerCategory> getAllCategory();

}
