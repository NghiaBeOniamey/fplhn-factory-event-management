package com.portalevent.core.admin.eventclosedmanagement.repository;

import com.portalevent.core.admin.eventclosedmanagement.model.response.AdminerPropsResponse;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminerCSemesterRepository extends SemesterRepository {
    @Query(value = """
            SELECT o.id AS id, o.name AS name FROM Semester o
            """, nativeQuery = true)
    List<AdminerPropsResponse> getAllSemester();

}
