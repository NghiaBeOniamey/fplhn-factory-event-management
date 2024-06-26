package com.portalevent.repository;

import com.portalevent.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(ResourceRepository.NAME)
public interface ResourceRepository extends JpaRepository<Resource, String> {

    public static final String NAME = "BaseResourceRepository";

}
