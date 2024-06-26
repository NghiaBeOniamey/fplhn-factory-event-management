package com.portalevent.core.admin.eventclosedmanagement.repository;

import com.portalevent.core.admin.eventclosedmanagement.model.request.AdminerEventCloseRequest;
import com.portalevent.core.admin.eventclosedmanagement.model.response.AdminerEventCloseResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminerEventCloseRepository extends EventRepository {

    @Query(value = """
            SELECT e.id as id, 
            	ROW_NUMBER() OVER(ORDER BY e.last_modified_date DESC) AS stt,
            	e.name AS name,
            	e.banner_path AS banner,
            	CASE 
            	WHEN e.block_number = 0 THEN 'Block 1'
            	WHEN e.block_number = 1 THEN 'Block 2'
            	END AS blockNumber,
            	sm.name AS semester,
            	GROUP_CONCAT(DISTINCT m.name) AS major,
            	GROUP_CONCAT(DISTINCT o.name) AS object,
            	c.name AS category,
            	e.reason
            	FROM event e
            	LEFT JOIN semester sm ON sm.id = e.semester_id
            	LEFT JOIN event_major em ON em.event_id = e.id
            	LEFT JOIN major m ON em.major_id = m.id
            	LEFT JOIN event_object eo ON eo.event_id = e.id
            	LEFT JOIN object o ON eo.object_id = o.id
            	LEFT JOIN category c ON c.id = e.category_id
            	WHERE e.status = 0
            	AND (:#{#req.name} IS NULL OR :#{#req.name} = '' OR e.name LIKE %:#{#req.name}%)
            	AND (:#{#req.category} IS NULL OR :#{#req.category} = '' OR e.category_id LIKE %:#{#req.category}%)
            	AND (:#{#req.object} IS NULL OR :#{#req.object} = '' OR eo.object_id LIKE %:#{#req.object}%)
            	AND (:#{#req.major} IS NULL OR :#{#req.major} = '' OR em.major_id LIKE %:#{#req.major}%)
            	AND (:#{#req.semester} IS NULL OR :#{#req.semester} = '' OR e.semester_id LIKE %:#{#req.semester}%)
            	AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode})
            	AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode})
            	GROUP BY e.id ORDER BY e.last_modified_date DESC
            """, countQuery = """
            	SELECT COUNT(e.id)
            	FROM event e
            	LEFT JOIN semester sm ON sm.id = e.semester_id
            	LEFT JOIN event_major em ON em.event_id = e.id
            	LEFT JOIN major m ON em.major_id = m.id
            	LEFT JOIN event_object eo ON eo.event_id = e.id
            	LEFT JOIN object o ON eo.object_id = o.id
            	LEFT JOIN category c ON c.id = e.category_id
            	WHERE e.status = 0
            	AND (:#{#req.name} IS NULL OR :#{#req.name} = '' OR e.name LIKE %:#{#req.name}%)
            	AND (:#{#req.category} IS NULL OR :#{#req.category} = '' OR e.category_id LIKE %:#{#req.category}%)
            	AND (:#{#req.object} IS NULL OR :#{#req.object} = '' OR eo.object_id LIKE %:#{#req.object}%)
            	AND (:#{#req.major} IS NULL OR :#{#req.major} = '' OR em.major_id LIKE %:#{#req.major}%)
            	AND (:#{#req.semester} IS NULL OR :#{#req.semester} = '' OR e.semester_id LIKE %:#{#req.semester}%)
            	AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode})
            	AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode})
            	GROUP BY e.id ORDER BY e.last_modified_date DESC
            """, nativeQuery = true)
    Page<AdminerEventCloseResponse> getAllEventClose(Pageable pageable, @Param("req") AdminerEventCloseRequest req, TokenFindRequest request);

}
