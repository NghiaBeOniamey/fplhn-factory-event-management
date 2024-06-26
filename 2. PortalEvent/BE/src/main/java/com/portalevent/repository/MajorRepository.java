package com.portalevent.repository;

import com.portalevent.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(MajorRepository.NAME)
public interface MajorRepository extends JpaRepository<Major, String> {
    public static final String NAME = "BaseMajorRepository";

}