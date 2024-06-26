package com.portalevent.core.organizer.importTutorials.repository;

import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsObjectResponse;
import com.portalevent.entity.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrImportTutorialsObjectRepository extends JpaRepository<Object, String> {

    @Query(value = """
        SELECT o.id, o.name FROM portal_event.object o where o.name like :#{#name}
    """, nativeQuery = true)
    Optional<OrImportTutorialsObjectResponse> getObjectByName(String name);

}
