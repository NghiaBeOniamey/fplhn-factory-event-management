package com.portalevent.repository;

import com.portalevent.entity.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(ObjectRepository.NAME)
public interface ObjectRepository extends JpaRepository<Object, String> {
    public static final String NAME = "BaseObjectRepository";

}
