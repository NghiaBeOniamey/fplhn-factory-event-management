package com.portalevent.core.organizer.eventRegister.repository;

import com.portalevent.core.organizer.eventRegister.model.response.OerMajorResponse;
import com.portalevent.repository.EventLocationRepository;
import com.portalevent.repository.SemesterRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OerEventLocationRepository extends EventLocationRepository {
}
