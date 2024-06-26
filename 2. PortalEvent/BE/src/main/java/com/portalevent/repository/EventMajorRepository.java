package com.portalevent.repository;

import com.portalevent.entity.EventMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(EventMajorRepository.NAME)
public interface EventMajorRepository extends JpaRepository<EventMajor, String> {
    public static final String NAME = "BaseEventMajorRepository";
}
