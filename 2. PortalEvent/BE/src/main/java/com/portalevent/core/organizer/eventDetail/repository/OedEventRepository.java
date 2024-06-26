package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventOverlapOrganizer;
import com.portalevent.core.organizer.eventDetail.model.response.OedEventResponse;
import com.portalevent.entity.Event;
import com.portalevent.repository.EventRepository;

import java.util.List;
import java.util.Optional;

import org.apache.el.parser.Token;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author SonPT
 */
@Repository
public interface OedEventRepository extends EventRepository {
    @Query(value = """
        SELECT DISTINCT e.id AS id,
            s.id as semesterId,
            s.name AS semester,
            s.start_time as semesterStart,
            s.end_time as semesterEnd,
            s.start_time_first_block as startTimeFirstBlock,
            s.end_time_first_block as endTimeFirstBlock,
            s.start_time_second_block as startTimeSecondBlock,
            s.end_time_second_block as endTimeSecondBlock,
            e.name AS name,
            c.id AS category,
            e.expected_participants AS expected_participants,
            e.block_number AS block_number,
            e.start_time AS start_time,
            e.end_time AS end_time,
            e.description AS description,
            e.status AS status,
            e.reason AS reason,
            e.background_path AS background,
            e.banner_path AS banner,
            e.standee_path as standee,
            e.event_type as event_type,
            e.approver_id as approver_id,
            e.approver_id as approver_code,
            e.created_date as created_date,
            e.number_participants as number_participants,
            e.is_hire_location as is_hire_location,
            e.is_hire_design_bg as is_hire_design_bg,
            e.is_hire_design_banner as is_hire_design_banner,
            e.is_hire_design_standee as is_hire_design_standee,
            e.is_attendance as is_attendance,
            e.is_open_registration as is_open_registration,
            e.is_wait_approval_periodically as is_wait_approval_periodically,
            e.approval_periocially_reason as approval_periocially_reason,
            e.is_conversion_honey_request as is_conversion_honey_request
        FROM event AS e
        LEFT JOIN category AS c ON e.category_id = c.id 
        LEFT JOIN semester AS s ON e.semester_id = s.id
        WHERE e.id = :idEvent
        """, nativeQuery = true)
    OedEventResponse detail(@Param("idEvent") String id );

    @Query(value = """
                SELECT a FROM Event AS a  
                JOIN EventOrganizer AS b ON a.id = b.eventId
                WHERE a.semesterId = :semesterId AND a.status = 4 AND b.organizerId = :currentUserId
            """)
    List<Event> getAllEventBySemesterId(@Param("semesterId") String semesterId, @Param("currentUserId") String currentUserId);

    @Query("""
            SELECT e.orderApprovalPriority FROM Event e 
            WHERE e.status = 3 AND e.orderApprovalPriority IS NOT NULL 
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.trainingFacilityCode LIKE :#{#request.currentTrainingFacilityCode})
            AND (:#{#request.currentSubjectCode} IS NULL OR e.subjectCode LIKE :#{#request.currentSubjectCode})
            ORDER BY e.orderApprovalPriority DESC LIMIT 1 
                     """)
    Byte getLastestOrderApprovalPriority(TokenFindRequest request);

    @Query(value = """
            SELECT new com.portalevent.core.organizer.eventDetail.model.response.OedEventOverlapOrganizer(e.id, e.name, e.startTime, e.endTime) 
            FROM Event e
            JOIN EventOrganizer o ON e.id = o.eventId
            WHERE o.organizerId = :organizerId
            AND e.startTime < :endTime and e.endTime > :startTime AND e.status = 4
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.trainingFacilityCode LIKE :#{#request.currentTrainingFacilityCode})
            AND (:#{#request.currentSubjectCode} IS NULL OR e.subjectCode LIKE :#{#request.currentSubjectCode})
            """)
    List<OedEventOverlapOrganizer> getEventOverlapOrganizer(@Param("organizerId") String id,
                                                            @Param("startTime") Long startTime,
                                                            @Param("endTime") Long endTime,
                                                            TokenFindRequest request);

    @Query(value = """
        select distinct * 
        from event as e 
        where e.id = :id and e.status in :status
        """, nativeQuery = true)
    Optional<Event> findByIdAndStatusIn(@Param("id") String id, @Param("status") List<Short> status);
}
