package com.portalevent.core.approver.eventclosed.repository;

import com.portalevent.core.approver.eventclosed.model.response.AecPropsResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AecSemesterRepository extends SemesterRepository {
    @Query(value = """
            SELECT o.id AS id, o.name AS name FROM Semester o
            """, nativeQuery = true)
    List<AecPropsResponse> getAllSemester();
}
