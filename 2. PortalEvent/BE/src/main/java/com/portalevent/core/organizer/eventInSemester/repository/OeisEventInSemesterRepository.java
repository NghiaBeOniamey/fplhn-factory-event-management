//package com.portalevent.core.organizer.eventInSemester.repository;
//
//import com.portalevent.core.common.TokenFindRequest;
//import com.portalevent.core.organizer.eventInSemester.model.request.OeisEventInSemesterRequest;
//import com.portalevent.core.organizer.eventInSemester.model.response.OeisEventInSemesterResponse;
//import com.portalevent.repository.EventRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface OeisEventInSemesterRepository extends EventRepository {
//    @Query(value = """
//            WITH EventFormality AS (
//                SELECT  e.id, GROUP_CONCAT(DISTINCT el.formality) AS formality,
//                GROUP_CONCAT(DISTINCT eor.organizer_code) AS organizer,
//                GROUP_CONCAT(DISTINCT m.code) AS major
//                FROM event e JOIN event_location el ON e.id = el.event_id
//                JOIN event_organizer eor ON e.id = eor.event_id
//                JOIN event_major em ON e.id = em.event_id
//                JOIN major m ON m.id = em.major_id
//                GROUP BY e.id
//            )
//            SELECT
//            e.id as id,
//            ROW_NUMBER() OVER(ORDER BY e.created_date DESC) AS indexs,
//            e.name AS name,
//            sm.name AS semester,
//            GROUP_CONCAT(DISTINCT o.name) AS object,
//            GROUP_CONCAT(DISTINCT c.name) AS category,
//            GROUP_CONCAT(DISTINCT el.formality) AS formality,
//            e.expected_participants AS expectedParticipant,
//            e.number_participants AS numberParticipant,
//            eventFor.organizer AS organizer,
//            e.status AS status
//            FROM event e
//            JOIN EventFormality eventFor ON eventFor.id = e.id
//            LEFT JOIN semester sm ON sm.id = e.semester_id
//            LEFT JOIN event_major em ON em.event_id = e.id
//            LEFT JOIN event_object eo ON eo.event_id = e.id
//            LEFT JOIN object o ON eo.object_id = o.id
//            LEFT JOIN category c ON c.id = e.category_id
//            LEFT JOIN event_organizer eor ON eor.event_id = e.id
//            LEFT JOIN event_location el ON el.event_id = e.id
//            WHERE e.event_type NOT IN (1)
//            AND (:#{#req.name} IS NULL OR :#{#req.name} = '' OR e.name LIKE %:#{#req.name}%)
//            AND (:#{#req.semester} IS NULL OR :#{#req.semester} = '' OR e.semester_id = :#{#req.semester})
//            AND (:#{#req.organizer} IS NULL OR :#{#req.organizer} = '' OR eor.organizer_code = :#{#req.organizer})
//            AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode})
//            AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode})
//            GROUP BY e.id
//            """, countQuery = """
//            WITH EventFormality AS (
//                SELECT  e.id, GROUP_CONCAT(DISTINCT el.formality) AS formality,
//                GROUP_CONCAT(DISTINCT eor.organizer_code) AS organizer,
//                GROUP_CONCAT(DISTINCT m.code) AS major
//                FROM event e JOIN event_location el ON e.id = el.event_id
//                JOIN event_organizer eor ON e.id = eor.event_id
//                JOIN event_major em ON e.id = em.event_id
//                JOIN major m ON m.id = em.major_id
//                GROUP BY e.id
//            )
//            SELECT
//            COUNT(*)
//            FROM event e
//            JOIN EventFormality eventFor ON eventFor.id = e.id
//            LEFT JOIN semester sm ON sm.id = e.semester_id
//            LEFT JOIN event_major em ON em.event_id = e.id
//            LEFT JOIN event_object eo ON eo.event_id = e.id
//            LEFT JOIN object o ON eo.object_id = o.id
//            LEFT JOIN category c ON c.id = e.category_id
//            LEFT JOIN event_organizer eor ON eor.event_id = e.id
//            LEFT JOIN event_location el ON el.event_id = e.id
//            WHERE e.event_type NOT IN (3)
//            AND (:#{#req.name} IS NULL OR :#{#req.name} = '' OR e.name LIKE %:#{#req.name}%)
//            AND (:#{#req.semester} IS NULL OR :#{#req.semester} = '' OR e.semester_id = :#{#req.semester})
//            AND (:#{#req.organizer} IS NULL OR :#{#req.organizer} = '' OR eor.organizer_code = :#{#req.organizer})
//            AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode})
//            AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode})
//            GROUP BY e.id
//            """, nativeQuery = true)
//    Page<OeisEventInSemesterResponse> getAll(@Param("req") OeisEventInSemesterRequest request, Pageable pageable, TokenFindRequest tokenFindRequest);
//}
