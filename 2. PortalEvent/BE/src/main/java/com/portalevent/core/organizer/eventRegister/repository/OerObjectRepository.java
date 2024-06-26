package com.portalevent.core.organizer.eventRegister.repository;

import com.portalevent.core.organizer.eventRegister.model.response.OerObjectResponse;
import com.portalevent.repository.ObjectRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OerObjectRepository extends ObjectRepository {

    @Query(value = "SELECT o.id, o.name FROM object o ORDER BY created_date DESC", nativeQuery = true)
    List<OerObjectResponse> getAll();
}
