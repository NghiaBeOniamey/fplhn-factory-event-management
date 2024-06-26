package com.portalevent.core.approver.eventclosed.repository;

import com.portalevent.core.approver.eventclosed.model.response.AecPropsResponse;
import com.portalevent.repository.ObjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AecObjectRepository extends ObjectRepository {
    @Query("""
            SELECT o.id AS id, o.name AS name FROM Object o
            """)
    List<AecPropsResponse> getAllObject();
}
