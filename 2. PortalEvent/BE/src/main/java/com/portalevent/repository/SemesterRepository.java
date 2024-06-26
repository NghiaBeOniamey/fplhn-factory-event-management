package com.portalevent.repository;

import com.portalevent.entity.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository
public interface SemesterRepository extends JpaRepository<Semester, String> {
    public static final String NAME = "BaseSemesterRepository";

}