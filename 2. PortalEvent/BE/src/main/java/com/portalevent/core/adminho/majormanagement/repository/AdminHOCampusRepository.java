package com.portalevent.core.adminho.majormanagement.repository;

import com.portalevent.core.adminho.majormanagement.model.response.AdminHOCampusResponse;
import com.portalevent.repository.CampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHOCampusRepository extends CampusRepository {

    @Query(value = """
            SELECT
                c.code as code,
                c.name as name
            FROM campus c
            """, nativeQuery = true)
    List<AdminHOCampusResponse> getCampusList();

}
