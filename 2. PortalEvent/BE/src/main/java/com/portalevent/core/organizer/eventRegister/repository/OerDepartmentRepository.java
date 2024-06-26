package com.portalevent.core.organizer.eventRegister.repository;

import com.portalevent.core.organizer.eventRegister.model.response.OerDepartmentResponse;
import com.portalevent.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OerDepartmentRepository extends DepartmentRepository {

    @Query(value = """
            SELECT
            	d.id,
             	d.code,
             	d.name,
             	dc.mail_of_manager,
             	d.department_id
            FROM department d
            	LEFT JOIN department_campus dc ON d.department_id = dc.department_id
            ORDER BY
            	d.created_date DESC
            """, nativeQuery = true)
    List<OerDepartmentResponse> getAll();

}
