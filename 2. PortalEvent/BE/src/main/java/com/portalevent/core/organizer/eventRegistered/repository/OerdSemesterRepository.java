package com.portalevent.core.organizer.eventRegistered.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventRegistered.model.response.OerdSemesterResponse;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OerdSemesterRepository extends SemesterRepository {

    @Query(value = """ 
            SELECT id, name FROM semester e
             ORDER BY name DESC
            """, nativeQuery = true)
    List<OerdSemesterResponse> getAll();

}
