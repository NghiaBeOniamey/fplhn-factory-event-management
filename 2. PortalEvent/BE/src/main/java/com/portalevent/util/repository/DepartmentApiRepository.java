package com.portalevent.util.repository;

import com.portalevent.core.adminho.statisticseventmanagement.model.request.AdminHOSCDRequest;
import com.portalevent.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DepartmentApiRepository extends DepartmentRepository {

    @Query(value = """
        SELECT DISTINCT d.code FROM event_major em
        LEFT JOIN major_campus mc ON em.major_id = mc.id
        LEFT JOIN department_campus dc ON mc.department_campus_id = dc.department_campus_id
        LEFT JOIN department d ON d.department_id = dc.department_id
        WHERE em.event_id = :eventId
    """, nativeQuery = true)
    List<String> getDepartmentCodeByEventId(String eventId);

}
