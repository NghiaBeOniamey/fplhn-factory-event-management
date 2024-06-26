package com.portalevent.repository;

import com.portalevent.entity.EventOrganizer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(EventOrganizerRepository.NAME)
public interface EventOrganizerRepository extends JpaRepository<EventOrganizer, String> {

    public static final String NAME = "BaseEventOrganizerRepository";

}
