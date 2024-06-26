package com.portalevent.core.admin.periodiceventmanagement.repository;

import com.portalevent.core.admin.periodiceventmanagement.model.request.AdminerDPDFindPeriodEventRequest;
import com.portalevent.core.admin.periodiceventmanagement.model.response.AdminerDPDPeriodicEventResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.infrastructure.projection.SimpleEntityProjection;
import com.portalevent.repository.PeriodicEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author thangncph26123
 */
public interface AdminerDPDPeriodicEventRepository extends PeriodicEventRepository {

    @Query(value = """
            WITH periodicEventObject AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS object FROM periodic_event a 
                LEFT JOIN periodic_event_object b ON a.id = b.periodic_event_id
                LEFT JOIN object c ON c.id = b.object_id
                GROUP BY a.id
            ), periodicEventMajor AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS major FROM periodic_event a 
                LEFT JOIN periodic_event_major b ON a.id = b.periodic_event_id
                LEFT JOIN major c ON c.id = b.major_id
                GROUP BY a.id
            )
            SELECT a.id, ROW_NUMBER() OVER(ORDER BY a.last_modified_date DESC) AS stt, 
            b.name AS category_name, a.name, a.event_type, a.expected_participants
            , peo.object, pem.major
            FROM periodic_event a 
            JOIN category b ON a.category_id = b.id
            LEFT JOIN periodic_event_major c ON a.id = c.periodic_event_id
            LEFT JOIN periodic_event_object d ON a.id = d.periodic_event_id
            LEFT JOIN periodicEventObject peo ON peo.id = a.id
            LEFT JOIN periodicEventMajor pem ON pem.id = a.id
            WHERE (:#{#req.name} IS NULL OR :#{#req.name} LIKE '' OR a.name LIKE %:#{#req.name}%)
            AND (:#{#req.categoryId} IS NULL OR :#{#req.categoryId} LIKE '' OR b.id LIKE %:#{#req.categoryId}%)
            AND (:#{#req.eventType} IS NULL OR :#{#req.eventType} LIKE '' OR a.event_type = :#{#req.eventType})
            AND (:#{#req.objectId} IS NULL OR :#{#req.objectId} LIKE '' OR d.object_id = :#{#req.objectId})
            AND (:#{#req.majorId} IS NULL OR :#{#req.majorId} LIKE '' OR c.major_id = :#{#req.majorId})
            GROUP BY a.id
            ORDER BY a.last_modified_date DESC
            """, countQuery = """
            WITH periodicEventObject AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS object FROM periodic_event a 
                JOIN periodic_event_object b ON a.id = b.periodic_event_id
                JOIN object c ON c.id = b.object_id
                GROUP BY a.id
            ), periodicEventMajor AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS major FROM periodic_event a 
                JOIN periodic_event_major b ON a.id = b.periodic_event_id
                JOIN major c ON c.id = b.major_id
                GROUP BY a.id
            )
            SELECT COUNT(1)
            FROM periodic_event a 
            JOIN category b ON a.category_id = b.id
            LEFT JOIN periodic_event_major c ON a.id = c.periodic_event_id
            LEFT JOIN periodic_event_object d ON a.id = d.periodic_event_id
            LEFT JOIN periodicEventObject peo ON peo.id = a.id
            LEFT JOIN periodicEventMajor pem ON pem.id = a.id
            WHERE (:#{#req.name} IS NULL OR :#{#req.name} LIKE '' OR a.name LIKE %:#{#req.name}%)
            AND (:#{#req.categoryId} IS NULL OR :#{#req.categoryId} LIKE '' OR b.id LIKE %:#{#req.categoryId}%)
            AND (:#{#req.eventType} IS NULL OR :#{#req.eventType} LIKE '' OR a.event_type = :#{#req.eventType})
            AND (:#{#req.objectId} IS NULL OR :#{#req.objectId} LIKE '' OR d.object_id = :#{#req.objectId})
            AND (:#{#req.majorId} IS NULL OR :#{#req.majorId} LIKE '' OR c.major_id = :#{#req.majorId})
            GROUP BY a.id
            ORDER BY a.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdminerDPDPeriodicEventResponse> getPage(Pageable pageable, @Param("req") AdminerDPDFindPeriodEventRequest req);

    @Query(value = """
            WITH eventObject AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS object FROM event a 
                LEFT JOIN event_object b ON a.id = b.event_id
                LEFT JOIN object c ON c.id = b.object_id
                GROUP BY a.id
            ), eventMajor AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS major FROM event a 
                LEFT JOIN event_major b ON a.id = b.event_id
                LEFT JOIN major c ON c.id = b.major_id
                GROUP BY a.id
            )
            SELECT a.id, ROW_NUMBER() OVER(ORDER BY a.last_modified_date DESC) AS stt, 
            b.name AS category_name, a.name, a.event_type, a.expected_participants
            , peo.object, pem.major
            FROM event a 
            JOIN category b ON a.category_id = b.id
            LEFT JOIN event_major c ON a.id = c.event_id
            LEFT JOIN event_object d ON a.id = d.event_id
            LEFT JOIN eventObject peo ON peo.id = a.id
            LEFT JOIN eventMajor pem ON pem.id = a.id
            WHERE (:#{#req.name} IS NULL OR :#{#req.name} LIKE '' OR a.name LIKE %:#{#req.name}%)
            AND (:#{#req.categoryId} IS NULL OR :#{#req.categoryId} LIKE '' OR b.id LIKE %:#{#req.categoryId}%)
            AND (:#{#req.eventType} IS NULL OR :#{#req.eventType} LIKE '' OR a.event_type = :#{#req.eventType})
            AND (:#{#req.objectId} IS NULL OR :#{#req.objectId} LIKE '' OR d.object_id = :#{#req.objectId})
            AND (:#{#req.majorId} IS NULL OR :#{#req.majorId} LIKE '' OR c.major_id = :#{#req.majorId})
            AND a.is_wait_approval_periodically = 1
            AND (:#{#request.currentSubjectCode} IS NULL OR a.subject_code LIKE :#{#request.currentSubjectCode})
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR a.training_facility_code LIKE :#{#request.currentTrainingFacilityCode})
            GROUP BY a.id
            ORDER BY a.last_modified_date DESC
            """, countQuery = """
            WITH eventObject AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS object FROM event a 
                LEFT JOIN event_object b ON a.id = b.event_id
                LEFT JOIN object c ON c.id = b.object_id
                GROUP BY a.id
            ), eventMajor AS (
                SELECT a.id, GROUP_CONCAT(c.name, ' ') AS major FROM event a 
                LEFT JOIN event_major b ON a.id = b.event_id
                LEFT JOIN major c ON c.id = b.major_id
                GROUP BY a.id
            )
            SELECT COUNT(1)
            FROM event a 
            JOIN category b ON a.category_id = b.id
            LEFT JOIN event_major c ON a.id = c.event_id
            LEFT JOIN event_object d ON a.id = d.event_id
            LEFT JOIN eventObject peo ON peo.id = a.id
            LEFT JOIN eventMajor pem ON pem.id = a.id
            WHERE (:#{#req.name} IS NULL OR :#{#req.name} LIKE '' OR a.name LIKE %:#{#req.name}%)
            AND (:#{#req.categoryId} IS NULL OR :#{#req.categoryId} LIKE '' OR b.id LIKE %:#{#req.categoryId}%)
            AND (:#{#req.eventType} IS NULL OR :#{#req.eventType} LIKE '' OR a.event_type = :#{#req.eventType})
            AND (:#{#req.objectId} IS NULL OR :#{#req.objectId} LIKE '' OR d.object_id = :#{#req.objectId})
            AND (:#{#req.majorId} IS NULL OR :#{#req.majorId} LIKE '' OR c.major_id = :#{#req.majorId})
            AND a.is_wait_approval_periodically = 1
            AND (:#{#request.currentSubjectCode} IS NULL OR a.subject_code LIKE :#{#request.currentSubjectCode})
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR a.training_facility_code LIKE :#{#request.currentTrainingFacilityCode})
            GROUP BY a.id
            ORDER BY a.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdminerDPDPeriodicEventResponse> getPageEventWaitApprover(Pageable pageable, @Param("req") AdminerDPDFindPeriodEventRequest req, TokenFindRequest request);

    @Query(value = """
            SELECT id, name FROM category ORDER BY created_date DESC
            """, nativeQuery = true)
    List<SimpleEntityProjection> getAllCategory();

    @Query(value = """
            SELECT id, name FROM object ORDER BY created_date DESC
            """, nativeQuery = true)
    List<SimpleEntityProjection> getAllObject();

    @Query(value = """
            SELECT id, name FROM major ORDER BY created_date DESC
            """, nativeQuery = true)
    List<SimpleEntityProjection> getAllMajor();

    @Query(value = """
            SELECT DISTINCT a.id FROM major a JOIN periodic_event_major b ON a.id = b.major_id
            WHERE b.periodic_event_id = :id
            """, nativeQuery = true)
    List<String> getAllMajorByIdPeriodicEvent(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM periodic_event_major a  
            LEFT JOIN periodic_event e ON a.periodic_event_id = e.id
            WHERE a.periodic_event_id = :id
            """, nativeQuery = true)
    void deletePeriodicEventMajor(@Param("id") String id);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM periodic_event_object a 
            LEFT JOIN periodic_event e ON a.periodic_event_id = e.id
            WHERE a.periodic_event_id = :id
            """, nativeQuery = true)
    void deletePeriodicEventObject(@Param("id") String id);

    @Query(value = """
            SELECT DISTINCT a.id 
            FROM object a JOIN periodic_event_object b ON a.id = b.object_id
            LEFT JOIN periodic_event e ON b.periodic_event_id = e.id
            WHERE b.periodic_event_id = :id
            """, nativeQuery = true)
    List<String> getAllObjectByIdPeriodicEvent(@Param("id") String id);

}
