package com.portalevent.core.organizer.importTutorials.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.importTutorials.model.request.OrEventImportTutorialsFilterRequest;
import com.portalevent.core.organizer.importTutorials.model.response.OrImportTutorialsResponse;
import com.portalevent.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrImportTutorialsEventRepository extends JpaRepository<Event, String> {
    @Query(value = """
        SELECT e.id AS id, e.name AS name, e.start_time AS start_time, e.end_time AS end_time ,
        em.major_id AS event_major_id, eot.object_id AS event_object_id
        FROM event e 
        JOIN event_organizer eo on e.id = eo.event_id
        JOIN event_major em on e.id = em.event_id 
        JOIN event_object eot on e.id = eot.event_id
        WHERE eo.organizer_id = :#{#request.idOrganizer} 
        AND (:#{#request.idSemester} LIKE '' OR :#{#request.idSemester} IS NULL OR  e.semester_id = :#{#request.idSemester})
        AND (:#{#request.blockNumber} LIKE '' OR :#{#request.blockNumber} IS NULL OR e.block_number = :#{#request.blockNumber})
        AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode} )
        AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode} ) 
        AND e.event_type = 3
        GROUP BY e.id, em.major_id, eot.object_id
        ORDER BY e.last_modified_date
    """, nativeQuery = true)
    List<OrImportTutorialsResponse> getAll(@Param("request") OrEventImportTutorialsFilterRequest request, TokenFindRequest tokenFindRequest);

    @Query(value = """
    SELECT * FROM event e 
    WHERE e.semester_id = :#{#idSemester} 
    AND e.event_type = 3
    """, nativeQuery = true)
    List<Event> getALlEventDelete(@Param("idSemester") String idSemester );
}
