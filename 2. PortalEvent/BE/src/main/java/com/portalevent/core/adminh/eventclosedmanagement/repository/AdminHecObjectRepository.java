package com.portalevent.core.adminh.eventclosedmanagement.repository;

import com.portalevent.core.adminh.eventclosedmanagement.model.response.AdminHecPropsResponse;
import com.portalevent.repository.ObjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHecObjectRepository extends ObjectRepository {

    @Query("""
            SELECT o.id AS id, o.name AS name FROM Object o
            """)
    List<AdminHecPropsResponse> getAllObject();

}
