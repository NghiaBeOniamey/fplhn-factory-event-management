package com.portalevent.core.organizer.importTutorials.repository;

import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsMajorResponse;
import com.portalevent.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrImportTutorialsMajorRepository extends JpaRepository<Major, String> {

    @Query(value = """
        SELECT m.id, m.name FROM major m where m.name like :#{#name}
    """, nativeQuery = true)
    Optional<OrImportTutorialsMajorResponse> getMajorByName(String name);
}
