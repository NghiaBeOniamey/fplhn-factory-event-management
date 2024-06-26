package com.portalevent.core.adminho.statisticseventmanagement.repository;

import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseDepartmentResponse;
import com.portalevent.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AdminHOseDepartmentRepository extends DepartmentRepository {

    @Query(value = """
            SELECT
                d.department_id as id,
                d.code as code,
                d.name as name
            FROM department d
            LEFT JOIN department_campus dc ON
            	d.department_id = dc.department_id
            LEFT JOIN campus c ON
            	dc.campus_id = c.campus_id
            WHERE
            	c.campus_id = :campusId
            """, nativeQuery = true)
    List<AdminHOseDepartmentResponse> getDepartmentByCampusId(Long campusId);

}
