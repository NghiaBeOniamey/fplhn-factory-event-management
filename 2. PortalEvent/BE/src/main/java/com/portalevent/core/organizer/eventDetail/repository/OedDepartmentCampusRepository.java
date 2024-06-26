package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedDepartmentCampusResponse;
import com.portalevent.repository.DepartmentCampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OedDepartmentCampusRepository extends DepartmentCampusRepository {

    @Query(value = """
            SELECT
            	d.id as 'id',
            	d.name as 'department_name',
            	dc.department_campus_id as 'department_campus_id',
            	d.code as 'code'
            FROM
            	department_campus dc
            LEFT JOIN department d on
            	dc.department_id = d.department_id 
            LEFT JOIN campus c on dc.campus_id = c.campus_id 
            WHERE (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode} );
            """, nativeQuery = true)
    List<OedDepartmentCampusResponse> getAllDepartmentsByCampus(TokenFindRequest request);

    @Query(value = """
                SELECT	DISTINCT
                		d.id as 'id',
            	        d.name as 'department_name',
            	        dc.department_campus_id as 'department_campus_id',
            	        d.code as 'code'
                FROM event e
                LEFT JOIN event_major em ON em.event_id = e.id
                LEFT JOIN major_campus mc ON em.major_id = mc.id
                LEFT JOIN major m ON mc.major_id = m.major_id
                LEFT JOIN department d ON m.department_id = d.department_id
                LEFT JOIN department_campus dc ON d.department_id = dc.department_id
                LEFT JOIN campus c on dc.campus_id = c.campus_id
                WHERE e.id = :idEvent
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode} );
                """,nativeQuery = true)
    List<OedDepartmentCampusResponse> getAllDepartmentsCampusByIdEvent(String idEvent,TokenFindRequest request);

}
