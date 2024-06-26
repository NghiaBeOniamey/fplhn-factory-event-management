package com.portalevent.core.organizer.hireDesignList.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventOrganizerResponse;
import com.portalevent.repository.EventOrganizerRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author thangncph26123
 */
@Repository
public interface OhdlEventOrganizerRepository extends EventOrganizerRepository {

    //Hiển thị role
    @Query(value = """
            SELECT a.id, a.event_id, a.organizer_id, a.event_role
            FROM event_organizer a 
            LEFT JOIN event e on el.event_id = e.id
            WHERE (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
            AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode} )
            ORDER BY a.event_role
            """, nativeQuery = true)
    List<OhdlEventOrganizerResponse> getAllEventOrganizer(TokenFindRequest request);

}
