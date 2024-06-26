package com.portalevent.core.adminho.eventclosedmanagement.repository;

import com.portalevent.core.adminho.eventclosedmanagement.model.response.AdminHOecPropsResponse;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHOecMajorRepository extends MajorRepository {

    @Query("""
            SELECT o.id AS id, o.name AS name FROM Major o
            """)
    List<AdminHOecPropsResponse> getAllMajor();

}
