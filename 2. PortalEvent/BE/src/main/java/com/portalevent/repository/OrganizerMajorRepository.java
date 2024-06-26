package com.portalevent.repository;

import com.portalevent.entity.OrganizerMajor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(OrganizerMajorRepository.NAME)
public interface OrganizerMajorRepository extends JpaRepository<OrganizerMajor, String> {
    public static final String NAME = "BaseOrganizerMajorRepository";

}
