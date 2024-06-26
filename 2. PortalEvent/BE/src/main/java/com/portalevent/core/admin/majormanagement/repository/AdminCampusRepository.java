package com.portalevent.core.admin.majormanagement.repository;

import com.portalevent.core.admin.majormanagement.model.response.AdminCampusResponse;
import com.portalevent.repository.CampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminCampusRepository extends CampusRepository {

    @Query(value = """
            SELECT
                c.code as code,
                c.name as name
            FROM campus c
            """, nativeQuery = true)
    List<AdminCampusResponse> getCampusList();

}
