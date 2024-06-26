package com.portalevent.repository;

import com.portalevent.entity.EventLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(EventLocationRepository.NAME)
public interface EventLocationRepository extends JpaRepository<EventLocation, String> {
    public static final String NAME = "BaseEventLocationRepository";

}
