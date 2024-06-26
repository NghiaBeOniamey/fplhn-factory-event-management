package com.portalevent.core.adminh.statisticseventmanagement.repository;

import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseDepartmentResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminHseDepartmentRepository extends DepartmentRepository {

    @Query(value = """
        SELECT DISTINCT d.id, d.code, d.name FROM major_campus mc
        LEFT JOIN department_campus dc ON mc.department_campus_id = dc.department_campus_id
        LEFT JOIN department d ON d.department_id = dc.department_id
        LEFT JOIN campus c ON c.campus_id = dc.campus_id
        LEFT JOIN major m ON m.major_id = mc.major_id
        WHERE c.code = :#{#request.currentTrainingFacilityCode}
    """, nativeQuery = true)
    List<AdminHseDepartmentResponse> getAllDepartment(TokenFindRequest request);

}
