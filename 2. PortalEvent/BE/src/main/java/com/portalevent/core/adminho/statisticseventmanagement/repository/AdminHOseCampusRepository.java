package com.portalevent.core.adminho.statisticseventmanagement.repository;

import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseCampusResponse;
import com.portalevent.repository.CampusRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminHOseCampusRepository extends CampusRepository {

    @Query(value = """
            SELECT
                c.campus_id as id,
                c.code as code,
                c.name as name
            FROM campus c
            """, nativeQuery = true)
    List<AdminHOseCampusResponse> getCampusList();

}
