package com.portalevent.core.organizer.hireDesignList.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventLocationResponse;
import com.portalevent.repository.EventLocationRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OhdlEventLocationRepository extends EventLocationRepository {

    //Hiển thị location
    @Query(value = """
            SELECT el.id,el.event_id, el.formality, el.path, el.name FROM event_location el
            LEFT JOIN event e on el.event_id = e.id
            WHERE (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
            AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode} )
            """, countQuery = """
            SELECT COUNT(*) FROM event_location el
            LEFT JOIN event e on el.event_id = e.id
            WHERE (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
            AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode} )
            """
            , nativeQuery = true)
    List<OhdlEventLocationResponse> getListFormalityOfHireDesign(TokenFindRequest request);

    @Query(value = """
            SELECT el.id,el.event_id, el.name, el.path FROM event_location el 
            WHERE el.path LIKE ?1 AND el.event_id = ?2
            """, countQuery = """
            SELECT COUNT(*) FROM event_location el
            WHERE el.path LIKE ?1 AND el.event_id = ?2
            """
            , nativeQuery = true)
    List<OhdlEventLocationResponse> findNameLocation(@Param("pathLocation") String pathLocation, @Param("idEvent") String idEvent );

    @Query(value = """
            SELECT el.id,el.event_id, el.name, el.path, el.formality  
            FROM event_location el
             WHERE el.event_id = ?1
            """, countQuery = """
            SELECT COUNT(*)  FROM event_location el
             WHERE el.event_id = ?1
            """
            , nativeQuery = true)
    List<OhdlEventLocationResponse> findLocationById(@Param("idEvent") String idEvent );

    @Query(value = """
            SELECT el.id, el.event_id,el.name, el.path, el.formality 
            FROM event_location el 
            WHERE el.id = ?1
            """, countQuery = """
            SELECT COUNT(*) 
            FROM event_location el 
            WHERE el.id = ?1
            """
            , nativeQuery = true)
    OhdlEventLocationResponse findOneLocationById(@Param("id") String id );
}
