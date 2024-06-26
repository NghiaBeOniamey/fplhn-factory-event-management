package com.portalevent.core.adminh.eventclosedmanagement.repository;

import com.portalevent.core.adminh.eventclosedmanagement.model.response.AdminHecPropsResponse;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHdecSemesterRepository extends SemesterRepository {

    @Query(value = """
            SELECT o.id AS id, o.name AS name FROM Semester o
            """, nativeQuery = true)
    List<AdminHecPropsResponse> getAllSemester();

}
