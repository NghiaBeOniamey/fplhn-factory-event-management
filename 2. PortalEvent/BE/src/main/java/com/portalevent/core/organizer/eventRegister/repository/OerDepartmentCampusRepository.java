package com.portalevent.core.organizer.eventRegister.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventRegister.model.response.OerDepartmentCampusResponse;
import com.portalevent.repository.DepartmentCampusRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OerDepartmentCampusRepository extends DepartmentCampusRepository {

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
            WHERE (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode});
            """, nativeQuery = true)
    List<OerDepartmentCampusResponse> getAllDepartmentsByCampus(TokenFindRequest request);

}
