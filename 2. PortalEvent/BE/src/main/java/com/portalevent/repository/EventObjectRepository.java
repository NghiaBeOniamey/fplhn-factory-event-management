package com.portalevent.repository;

import com.portalevent.entity.EventObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */
@Repository(EventObjectRepository.NAME)
public interface EventObjectRepository extends JpaRepository<EventObject, String> {
    public static final String NAME = "BaseEventObjectRepository";

}
