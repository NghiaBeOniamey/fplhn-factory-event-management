package com.portalevent.core.adminho.eventclosedmanagement.repository;

import com.portalevent.core.adminho.eventclosedmanagement.model.response.AdminHOecPropsResponse;
import com.portalevent.repository.ObjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminHOecObjectRepository extends ObjectRepository {

    @Query("""
            SELECT o.id AS id, o.name AS name FROM Object o
            """)
    List<AdminHOecPropsResponse> getAllObject();

}
