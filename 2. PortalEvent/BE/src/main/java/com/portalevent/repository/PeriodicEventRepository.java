package com.portalevent.repository;

import com.portalevent.entity.PeriodicEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(PeriodicEventRepository.NAME)
public interface PeriodicEventRepository extends JpaRepository<PeriodicEvent, String> {
    public static final String NAME = "BasePeriodicEventRepository";

}