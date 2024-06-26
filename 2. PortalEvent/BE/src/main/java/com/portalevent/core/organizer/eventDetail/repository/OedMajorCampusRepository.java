package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedMajorCampusResponse;
import com.portalevent.repository.DepartmentCampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OedMajorCampusRepository extends DepartmentCampusRepository {

    @Query(value = """
            SELECT
                DISTINCT
            	mc.id AS majorCampusId,
            	mc.department_campus_id AS departmentCampusId,
            	m.name AS majorName,
            	d.id AS departmentId
            FROM major_campus mc
            LEFT JOIN major m ON mc.major_id = m.major_id
            LEFT JOIN department_campus dc ON mc.department_campus_id = dc.department_campus_id
            LEFT JOIN  department d ON d.department_id = dc.department_id
            LEFT JOIN campus c on dc.campus_id = c.campus_id
            WHERE FIND_IN_SET(d.id,:idDepartment) AND
            (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode} );
            """, nativeQuery = true)
    List<OedMajorCampusResponse> getAllMajorCampus(String idDepartment,TokenFindRequest request);

    @Query(value = """
            SELECT 
            	mc.id AS majorCampusId,
            	mc.department_campus_id AS departmentCampusId,
            	m.name AS majorName,
            	d.id AS departmentId
            FROM
            	event e
            LEFT JOIN event_major em ON em.event_id = e.id
            LEFT JOIN major_campus mc ON em.major_id = mc.id
            LEFT JOIN major m ON mc.major_id = m.major_id
            LEFT JOIN department d ON m.department_id = d.department_id
            LEFT JOIN department_campus dc ON d.department_id = dc.department_id
            LEFT JOIN campus c on dc.campus_id = c.campus_id
            WHERE e.id = :idEvent
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode} );
            """, nativeQuery = true)
    List<OedMajorCampusResponse> getAllMajorCampusByIdEvent(String idEvent,TokenFindRequest request);

}
