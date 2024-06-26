package com.portalevent.core.admin.statisticseventmanagement.repository;

import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerDepartmentResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminerseDepartmentRepository extends MajorRepository {

    @Query(value = """
        SELECT DISTINCT d.id, d.code, d.name FROM major_campus mc
        LEFT JOIN department_campus dc ON mc.department_campus_id = dc.department_campus_id
        LEFT JOIN department d ON d.department_id = dc.department_id
        LEFT JOIN campus c ON c.campus_id = dc.campus_id
        LEFT JOIN major m ON m.major_id = mc.major_id
        WHERE c.code = :#{#request.currentTrainingFacilityCode}
        """, nativeQuery = true)
    List<AdminerDepartmentResponse> getAllDepartment(TokenFindRequest request);

}
