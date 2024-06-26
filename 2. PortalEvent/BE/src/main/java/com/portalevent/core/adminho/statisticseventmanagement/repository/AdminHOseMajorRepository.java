package com.portalevent.core.adminho.statisticseventmanagement.repository;

import com.portalevent.core.adminho.statisticseventmanagement.model.request.AdminHOSCDRequest;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseCampusResponse;
import com.portalevent.core.adminho.statisticseventmanagement.model.response.AdminHOseMajorResponse;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminHOseMajorRepository extends MajorRepository {

    @Query(value = """
        SELECT DISTINCT d.id, d.code, d.name FROM major_campus mc
        LEFT JOIN department_campus dc ON mc.department_campus_id = dc.department_campus_id
        LEFT JOIN department d ON d.department_id = dc.department_id
        LEFT JOIN campus c ON c.campus_id = dc.campus_id
        LEFT JOIN major m ON m.major_id = mc.major_id
        WHERE c.campus_id = :#{#req.idCampus}
    """, nativeQuery = true)
    List<AdminHOseMajorResponse> getAllMajor(AdminHOSCDRequest req);

}
