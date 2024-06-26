package com.portalevent.core.admin.statisticseventmanagement.repository;

import com.portalevent.core.admin.statisticseventmanagement.model.response.AdminerSemesterResponse;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminerseSemesterRepository extends SemesterRepository {

    @Query(value = """
            SELECT s.id, s.name, s.start_time, s.end_time FROM semester AS s
            """, nativeQuery = true)
    List<AdminerSemesterResponse> getAllSemester();

}
