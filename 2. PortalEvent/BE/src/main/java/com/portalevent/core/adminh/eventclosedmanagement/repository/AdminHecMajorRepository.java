package com.portalevent.core.adminh.eventclosedmanagement.repository;

import com.portalevent.core.adminh.eventclosedmanagement.model.response.AdminHecPropsResponse;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHecMajorRepository extends MajorRepository {

    @Query("""
            SELECT o.id AS id, o.name AS name FROM Major o
            """)
    List<AdminHecPropsResponse> getAllMajor();

}
