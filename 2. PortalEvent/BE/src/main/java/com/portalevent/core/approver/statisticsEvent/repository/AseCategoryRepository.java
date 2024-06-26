package com.portalevent.core.approver.statisticsEvent.repository;

import com.portalevent.core.approver.statisticsEvent.model.response.AseCategory;
import com.portalevent.repository.CategoryRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface AseCategoryRepository extends CategoryRepository {
    @Query(value = """
            SELECT id, name FROM category
            """, nativeQuery = true)
    List<AseCategory> getAllCategory();
}
