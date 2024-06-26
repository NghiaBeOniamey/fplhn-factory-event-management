package com.portalevent.core.organizer.mutipleregister.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrEventDetailResponse;
import com.portalevent.core.organizer.mutipleregister.model.response.OmrEventScheduleResponse;
import com.portalevent.repository.EventRepository;
import org.apache.el.parser.Token;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OmrEventRepository extends EventRepository {

    @Query(value = """
            SELECT a.id, a.name, a.start_time, a.end_time, 
            (CASE WHEN b.organizer_id = :userId AND a.status = 0 THEN 0
            	WHEN b.organizer_id = :userId AND a.status = 1 THEN 1
            	WHEN b.organizer_id = :userId AND a.status = 2 THEN 2
            	WHEN b.organizer_id = :userId AND a.status = 3 THEN 3
            	WHEN b.organizer_id = :userId AND a.status = 4 THEN 4
            	WHEN b.organizer_id = :userId AND a.status = 5 THEN 5
            	ELSE -1 END) AS isOwnEvent
            FROM event a
            LEFT JOIN event_organizer b ON a.id = b.event_id
            WHERE ( a.status IN (4,5) OR b.organizer_id = :userId)  
                  AND ( :#{#request.currentSubjectCode} IS NULL OR a.subject_code LIKE :#{#request.currentSubjectCode} )
                  AND (:#{#request.currentTrainingFacilityCode} IS NULL OR a.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )                                            
            """, nativeQuery = true)
    List<OmrEventScheduleResponse> getAllForCalendar(@Param("userId") String userId, TokenFindRequest request);

    @Query(value = """
            SELECT a.id, a.name, a.start_time, a.end_time, b.name AS category_name, c.name AS semester_name, a.status, 
            (CASE WHEN a.block_number = 0 THEN 'Block 1' ELSE 'Block 2' END) AS block_number, a.expected_participants, 
            a.event_type, e.name AS object_name, g.name AS major_name, h.organizer_id
            FROM event a
            LEFT JOIN category b ON a.category_id = b.id
            LEFT JOIN semester c ON a.semester_id = c.id
            LEFT JOIN event_object d ON a.id = d.event_id
            LEFT JOIN object e ON d.object_id = e.id 
        	LEFT JOIN event_major f ON a.id = f.event_id
        	LEFT JOIN major g ON f.major_id = g.id
        	LEFT JOIN event_organizer h ON a.id = h.event_id
            WHERE a.id = :id
            """, nativeQuery = true)
    List<OmrEventDetailResponse.EventResponse> getDetailEvent(@Param("id") String id );

}
