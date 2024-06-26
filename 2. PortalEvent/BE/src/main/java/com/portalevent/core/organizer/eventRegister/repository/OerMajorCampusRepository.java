package com.portalevent.core.organizer.eventRegister.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventRegister.model.response.OerDepartmentCampusResponse;
import com.portalevent.core.organizer.eventRegister.model.response.OerMajorCampusResponse;
import com.portalevent.repository.DepartmentCampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OerMajorCampusRepository extends DepartmentCampusRepository {

    @Query(value = """
            SELECT
            	mc.id AS majorCampusId,
             	mc.department_campus_id AS departmentCampusId,
            	m.name AS majorName,
            	d.id AS departmentId
            FROM major_campus mc
            LEFT JOIN major m ON mc.major_id = m.major_id
            LEFT JOIN department d ON m.department_id = d.department_id
            """, nativeQuery = true)
    List<OerMajorCampusResponse> getAllMajorCampus();

    @Query(value = """
            SELECT
            	mc.id,
            	mc.department_campus_id,
            	m.name
            FROM
            	event e
            LEFT JOIN event_major em ON
            	em.event_id = e.id
            LEFT JOIN major_campus mc ON
            	em.major_id = mc.id
            LEFT JOIN major m ON
            	mc.major_id = m.major_id
            WHERE e.id = :idEvent
            """, nativeQuery = true)
    List<OerMajorCampusResponse> getAllMajorCampusByIdEvent(String idEvent);

}
