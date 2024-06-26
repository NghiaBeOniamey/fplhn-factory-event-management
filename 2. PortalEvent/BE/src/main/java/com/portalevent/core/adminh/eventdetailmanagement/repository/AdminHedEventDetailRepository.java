package com.portalevent.core.adminh.eventdetailmanagement.repository;

import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedEventDetailApprovedResponse;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedEventMajorResponse;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedEventObjectResponse;
import com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedEventOverlapOrganizer;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.entity.Event;
import com.portalevent.repository.EventRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminHedEventDetailRepository extends EventRepository {

    @Query(value = """
            SELECT
                        e.id AS id,
                        e.name AS name,
                        e.start_time AS startTime,
                        e.end_time AS endTime,
                        c.name AS categoryName,
                        e.description AS description,
                        e.approver_id AS approverId,
                        e.reason AS reason,
                        e.expected_participants AS expectedParticipant,
                        e.status AS status,
                        e.background_path AS background,
                        e.banner_path AS banner,
                        e.standee_path AS standee,
                        e.event_type AS event_type,
                        e.is_hire_design_banner AS isHireDesignBanner,
                        e.is_hire_design_bg AS isHireDesignBackground,
                        e.is_hire_design_standee AS isHireDesignStandee,
                        p1.numberParticipants AS numberParticipants,
                        e.is_wait_approval_periodically AS isWaitApprovalPeriodically,
                        p1.participants,
                        e.is_not_enough_time_approval,
                        ROUND(
                               (SELECT COUNT(*)
                                FROM participant
                                WHERE event_id = e.id AND rate IN (4, 5)) /
                               (SELECT COUNT(*)
                                FROM participant
                                WHERE event_id = e.id) * 100, 1
                                 ) AS avgRate,
                        GROUP_CONCAT(DISTINCT d.name SEPARATOR ',') AS departmentName,
                        GROUP_CONCAT(DISTINCT m.name SEPARATOR ',') AS majorName
                        FROM event e
                        LEFT join (SELECT event_id, COUNT(IF(participant.participant_type =1 , 1, NULL)) AS participants,
                      					 COUNT(IF(participant.participant_type = 0 , 1, NULL)) as numberParticipants
                               FROM participant
                               GROUP BY event_id) p1  ON e.id = p1.event_id
                        LEFT JOIN category c ON c.id = e.category_id
                        LEFT JOIN event_major em ON em.event_id = e.id
                        LEFT JOIN major_campus mc ON em.major_id = mc.id
                        LEFT JOIN major m ON mc.major_id = m.major_id
                        LEFT JOIN department d ON m.department_id = d.department_id
                        WHERE e.id = :id
                        GROUP BY e.id             
            """, nativeQuery = true)
    Optional<AdminHedEventDetailApprovedResponse> getDetailApprovedById(@Param("id") String id);

    @Query(value = """
            SELECT a.id, a.major_id, a.event_id 
            FROM event_major a 
            LEFT JOIN event e ON a.event_id = e.id
            where a.event_id = :id
            """, nativeQuery = true)
    List<AdminHedEventMajorResponse> getAllEventMajorByIdEvent(@Param("id") String id);

    @Query(value = """
            SELECT a.id, a.object_id, a.event_id 
            FROM event_object a 
            LEFT JOIN event e ON a.event_id = e.id
            WHERE a.event_id = :id    
            """, nativeQuery = true)
    List<AdminHedEventObjectResponse> getAllEventObjectByIdEvent(@Param("id") String id);

    @Query(value = """
        select distinct * 
        from event as e 
        where e.id = :id and e.status in :status
        """, nativeQuery = true)
    Optional<Event> findByIdAndStatusIn(@Param("id") String id, @Param("status") List<Short> status);

    @Query(value = """
               select c.name from event e join category c on e.category_id = c.id
               where e.id = :idEvent
            """, nativeQuery = true)
    String getCategoryByIdEvent(@Param("idEvent") String idEvent);

    @Query(value = """
			SELECT GROUP_CONCAT(e.name SEPARATOR ', ') AS name  
			FROM event e 
			WHERE e.start_time < :endTime AND e.end_time > :startTime AND e.status = 4 AND e.id <> :id
			AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode})
			AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode})
            """, nativeQuery = true)
    String getNameEventsInTime(@Param("id") String id, @Param("startTime") Long startTime, @Param("endTime") Long endTime, TokenFindRequest request);

    @Query(value = """
            SELECT new com.portalevent.core.adminh.eventdetailmanagement.model.response.AdminHedEventOverlapOrganizer(e.id, e.name, e.startTime, e.endTime) 
            FROM Event e
            JOIN EventOrganizer o ON e.id = o.eventId
            WHERE o.organizerId = :organizerId
            and e.startTime < :endTime and e.endTime > :startTime AND e.status = 4 OR e.status = 3
            """)
    List<AdminHedEventOverlapOrganizer> getEventOverlapOrganizer(@Param("organizerId") String id,
                                                                 @Param("startTime") Long startTime,
                                                                 @Param("endTime") Long endTime);

}
