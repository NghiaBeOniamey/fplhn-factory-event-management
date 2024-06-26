package com.portalevent.repository;

import com.portalevent.entity.Evidence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */

@Repository(EvidenceRepository.NAME)
public interface EvidenceRepository extends JpaRepository<Evidence, String> {

    public static final String NAME = "BaseEvidenceRepository";

}