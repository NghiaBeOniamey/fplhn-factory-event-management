package com.portalevent.core.admin.eventclosedmanagement.repository;

import com.portalevent.core.admin.eventclosedmanagement.model.response.AdminerPropsResponse;
import com.portalevent.repository.MajorRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminerCMajorRepository extends MajorRepository {
    @Query("""
            SELECT o.id AS id, o.name AS name FROM Major o
            """)
    List<AdminerPropsResponse> getAllMajor();

}
