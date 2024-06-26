package com.portalevent.repository;

import com.portalevent.entity.PeriodicEventMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(PeriodicEventMajorRepository.NAME)
public interface PeriodicEventMajorRepository extends JpaRepository<PeriodicEventMajor, String> {
    public static final String NAME = "BasePeriodicEventMajorRepository";

}
