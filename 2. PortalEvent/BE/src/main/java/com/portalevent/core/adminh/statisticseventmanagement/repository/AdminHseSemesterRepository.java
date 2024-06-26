package com.portalevent.core.adminh.statisticseventmanagement.repository;

import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseSemesterResponse;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminHseSemesterRepository extends SemesterRepository {

    @Query(value = """
            SELECT s.id, s.name, s.start_time, s.end_time FROM semester AS s
            """, nativeQuery = true)
    List<AdminHseSemesterResponse> getAllSemester();

}
