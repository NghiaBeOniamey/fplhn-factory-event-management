package com.portalevent.repository;

import com.portalevent.entity.PeriodicEventObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(PeriodicEventObjectRepository.NAME)
public interface PeriodicEventObjectRepository extends JpaRepository<PeriodicEventObject, String> {
    public static final String NAME = "BasePeriodicEventObjectRepository";

}
