package com.portalevent.repository;

import com.portalevent.entity.SystemOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(SystemOptionRepository.NAME)
public interface SystemOptionRepository extends JpaRepository<SystemOption, Long> {

    public static final String NAME = "BaseSystemOptionRepository";

}
