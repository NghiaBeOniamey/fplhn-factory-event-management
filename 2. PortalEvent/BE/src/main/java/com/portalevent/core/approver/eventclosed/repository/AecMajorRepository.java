package com.portalevent.core.approver.eventclosed.repository;

import com.portalevent.core.approver.eventclosed.model.response.AecPropsResponse;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AecMajorRepository extends MajorRepository {
    @Query("""
            SELECT o.id AS id, o.name AS name FROM Major o
            """)
    List<AecPropsResponse> getAllMajor();
}
