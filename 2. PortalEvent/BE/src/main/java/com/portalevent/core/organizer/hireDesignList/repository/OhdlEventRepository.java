package com.portalevent.core.organizer.hireDesignList.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.hireDesignList.model.request.OhdlSearchEventRequest;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventLocationResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlEventResponse;
import com.portalevent.core.organizer.hireDesignList.model.response.OhdlImageEventResponse;
import com.portalevent.repository.EventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OhdlEventRepository extends EventRepository {

    //Hiển thị Event Hire Design
    @Query(value = """
            WITH EventFormality AS (
                SELECT  e.id, GROUP_CONCAT(DISTINCT el.formality) AS formality,
                GROUP_CONCAT(DISTINCT eor.organizer_id) AS organizer,
                GROUP_CONCAT(DISTINCT m.code SEPARATOR ', ') AS major
                FROM event e LEFT JOIN event_location el ON e.id = el.event_id
                JOIN event_organizer eor ON e.id = eor.event_id
                JOIN event_major em ON e.id = em.event_id
                JOIN major m ON m.id = em.major_id
                WHERE (e.is_hire_location = 1 OR e.is_hire_design_banner = 1
                OR e.is_hire_design_standee =1 OR e.is_hire_design_bg = 1 ) AND e.status = 4
                AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode} )
                AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode} )
                GROUP BY e.id
            )
            SELECT
              e.id, e.name, MAX(e.start_time) AS start_time, MAX(e.end_time) AS end_time,
                GROUP_CONCAT(DISTINCT c.name SEPARATOR ', ') AS category, MAX(e.event_type) AS event_type,
                eventFor.formality AS formality, GROUP_CONCAT(DISTINCT el.name SEPARATOR ', ') AS location,
                eventFor.organizer AS organizer,
                CONCAT(GROUP_CONCAT(DISTINCT s.name), ", ", CASE
                WHEN e.block_number = 1 THEN 'block 2'
                WHEN e.block_number = 0 THEN 'block 1'
                END) AS semester,
                GROUP_CONCAT(DISTINCT o.name) AS object, eventFor.major AS major,
                MAX(e.is_hire_design_banner) AS is_hire_design_banner,
                MAX(e.is_hire_design_standee) AS is_hire_design_standee,
                MAX(e.is_hire_design_bg) AS is_hire_design_bg,
                MAX(e.is_hire_location) AS is_hire_location,
                MAX(e.status) AS status
                FROM event e JOIN category c ON e.category_id = c.id
                JOIN semester s ON e.semester_id = s.id
                LEFT JOIN event_location el ON e.id = el.event_id
                JOIN event_major em ON e.id = em.event_id
                JOIN major m ON m.id = em.major_id
                JOIN event_organizer eor ON e.id = eor.event_id
                JOIN event_object eo ON e.id = eo.event_id
                JOIN object o ON o.id = eo.object_id
                JOIN EventFormality eventFor ON eventFor.id = e.id
                WHERE (e.is_hire_location = 1 OR e.is_hire_design_banner = 1
                OR e.is_hire_design_standee =1 OR e.is_hire_design_bg = 1 ) AND e.status = 4
            AND(:#{#req.startTimeLong} IS NULL OR :#{#req.startTimeLong} LIKE 'null'
            OR :#{#req.startTimeLong} LIKE '' OR e.start_time >= :#{#req.startTimeLong} )
            AND(:#{#req.endTimeLong} IS NULL OR :#{#req.endTimeLong} LIKE 'null'
            OR :#{#req.endTimeLong} LIKE '' OR e.end_time <= :#{#req.endTimeLong} )
            AND (em.major_id = :#{#req.idMajor} OR :#{#req.idMajor} IS NULL  OR :#{#req.idMajor} LIKE '' OR :#{#req.idMajor} LIKE 'null')
            AND (eor.organizer_id = :#{#req.idOrganizer} OR :#{#req.idOrganizer} IS NULL OR :#{#req.idOrganizer} LIKE '' OR :#{#req.idOrganizer} LIKE 'null')
            AND (:#{#req.formality} IS NULL OR :#{#req.formality} LIKE '' OR :#{#req.formality} LIKE 'null' OR el.formality = :#{#req.formality})
            AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode} )
            AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode} )
               
            GROUP BY e.id;
                """, countQuery = """
            WITH EventFormality AS (
                SELECT  e.id, GROUP_CONCAT(DISTINCT el.formality) AS formality,
                GROUP_CONCAT(DISTINCT eor.organizer_id) AS organizer,
                GROUP_CONCAT(DISTINCT m.code SEPARATOR ', ') AS major
                FROM event e LEFT JOIN event_location el ON e.id = el.event_id
                JOIN event_organizer eor ON e.id = eor.event_id
                JOIN event_major em ON e.id = em.event_id
                JOIN major m ON m.id = em.major_id
                WHERE (e.is_hire_location = 1 OR e.is_hire_design_banner = 1
                OR e.is_hire_design_standee =1 OR e.is_hire_design_bg = 1 ) AND e.status = 4
                AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode} )
                AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode} )               
                GROUP BY e.id
            )
            SELECT
            count(*)
            FROM event e JOIN category c ON e.category_id = c.id
                  JOIN semester s ON e.semester_id = s.id
                  LEFT JOIN event_location el ON e.id = el.event_id
                  JOIN event_major em ON e.id = em.event_id
                  JOIN major m ON m.id = em.major_id
                  JOIN event_organizer eor ON e.id = eor.event_id
                  JOIN event_object eo ON e.id = eo.event_id
                  JOIN object o ON o.id = eo.object_id
                  JOIN EventFormality eventFor ON eventFor.id = e.id
                  WHERE (e.is_hire_location = 1 OR e.is_hire_design_banner = 1
                OR e.is_hire_design_standee =1 OR e.is_hire_design_bg = 1 ) AND e.status = 4
            AND(:#{#req.startTimeLong} IS NULL OR :#{#req.startTimeLong} LIKE 'null'
            OR :#{#req.startTimeLong} LIKE '' OR e.start_time >= :#{#req.startTimeLong} )
            AND(:#{#req.endTimeLong} IS NULL OR :#{#req.endTimeLong} LIKE 'null'
            OR :#{#req.endTimeLong} LIKE '' OR e.end_time <= :#{#req.endTimeLong} )
            AND (em.major_id = :#{#req.idMajor} OR :#{#req.idMajor} IS NULL  OR :#{#req.idMajor} LIKE '' OR :#{#req.idMajor} LIKE 'null')
            AND (eor.event_id = :#{#req.idOrganizer} OR :#{#req.idOrganizer} IS NULL OR :#{#req.idOrganizer} LIKE '' OR :#{#req.idOrganizer} LIKE 'null')
            AND (:#{#req.formality} IS NULL OR :#{#req.formality} LIKE '' OR :#{#req.formality} LIKE 'null' OR el.formality = :#{#req.formality})
            AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode} )
            AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode} )               
            GROUP BY e.id;
              """, nativeQuery = true)
    Page<OhdlEventResponse> getAllHireDesignList(Pageable pageable, @Param("req") OhdlSearchEventRequest request, TokenFindRequest tokenFindRequest);


    @Query(value = """
            SELECT id, name,start_time, end_time, background_path, standee_path, banner_path 
            FROM event WHERE  id = ?1
            """, nativeQuery = true)
    OhdlImageEventResponse getAllImageById(@Param("id") String id );

    //Hiển thị 1 event hire design trong list hire design
    @Query(value = """
            WITH EventFormality AS (
                SELECT  e.id, GROUP_CONCAT(DISTINCT el.formality) AS formality,
                GROUP_CONCAT(DISTINCT eor.organizer_id) AS organizer,
                GROUP_CONCAT(DISTINCT m.code) AS major
                FROM event e LEFT JOIN event_location el ON e.id = el.event_id
                JOIN event_organizer eor ON e.id = eor.event_id
                JOIN event_major em ON e.id = em.event_id
                JOIN major m ON m.id = em.major_id
               WHERE (e.is_hire_location = 1 OR e.is_hire_design_banner = 1
                OR e.is_hire_design_standee =1 OR e.is_hire_design_bg = 1 ) AND e.status = 4
                AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode} )
                AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode} )               
                GROUP BY e.id
            )
            SELECT
             e.id, e.name, MAX(e.start_time) AS start_time, MAX(e.end_time) AS end_time,
                GROUP_CONCAT(DISTINCT c.name) AS category, MAX(e.event_type) AS event_type,
                eventFor.formality AS formality, GROUP_CONCAT(DISTINCT el.name) AS location,
                eventFor.organizer AS organizer,
                CONCAT(GROUP_CONCAT(DISTINCT s.name), ", ", CASE
                WHEN e.block_number = 1 THEN 'block 2'
                WHEN e.block_number = 0 THEN 'block 1'
                END) AS semester,
                GROUP_CONCAT(DISTINCT o.name) AS object, eventFor.major AS major,
                MAX(e.is_hire_design_banner) AS is_hire_design_banner,
                MAX(e.is_hire_design_standee) AS is_hire_design_standee,
                MAX(e.is_hire_design_bg) AS is_hire_design_bg,
                MAX(e.is_hire_location) AS is_hire_location,
                MAX(e.status) AS status
                FROM event e JOIN category c ON e.category_id = c.id
                JOIN semester s ON e.semester_id = s.id
                LEFT JOIN event_location el ON e.id = el.event_id
                JOIN event_major em ON e.id = em.event_id
                JOIN major m ON m.id = em.major_id
                JOIN event_organizer eor ON e.id = eor.event_id
                JOIN event_object eo ON e.id = eo.event_id
                JOIN object o ON o.id = eo.object_id
                JOIN EventFormality eventFor ON eventFor.id = e.id
                WHERE (e.is_hire_location = 1 OR e.is_hire_design_banner = 1
                OR e.is_hire_design_standee =1 OR e.is_hire_design_bg = 1 ) AND e.status = 4
                AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode} )
                AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode} )               
            AND e.id = ?1
             GROUP BY e.id;
               """, countQuery = """
             WITH EventFormality AS (
                SELECT  e.id, GROUP_CONCAT(DISTINCT el.formality) AS formality,
                GROUP_CONCAT(DISTINCT eor.organizer_id) AS organizer,
                GROUP_CONCAT(DISTINCT m.code) AS major
                FROM event e LEFT JOIN event_location el ON e.id = el.event_id
                JOIN event_organizer eor ON e.id = eor.event_id
                JOIN event_major em ON e.id = em.event_id
                JOIN major m ON m.id = em.major_id
                WHERE (e.is_hire_location = 1 OR e.is_hire_design_banner = 1
                OR e.is_hire_design_standee =1 OR e.is_hire_design_bg = 1 ) AND e.status = 4
                AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode} )
                AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode} ) 
                GROUP BY e.id
            )
             SELECT
             count(*)
             FROM event e JOIN category c ON e.category_id = c.id
                JOIN semester s ON e.semester_id = s.id
                LEFT JOIN event_location el ON e.id = el.event_id
                JOIN event_major em ON e.id = em.event_id
                JOIN major m ON m.id = em.major_id
                JOIN event_organizer eor ON e.id = eor.event_id
                JOIN event_object eo ON e.id = eo.event_id
                JOIN object o ON o.id = eo.object_id
                JOIN EventFormality eventFor ON eventFor.id = e.id
                WHERE (e.is_hire_location = 1 OR e.is_hire_design_banner = 1
                OR e.is_hire_design_standee =1 OR e.is_hire_design_bg = 1 ) AND e.status = 4
                AND (:#{#tokenFindRequest.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#tokenFindRequest.currentTrainingFacilityCode} )
                AND (:#{#tokenFindRequest.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#tokenFindRequest.currentSubjectCode} ) 
            AND e.id = ?1
             GROUP BY e.id;
             """, nativeQuery = true)
    OhdlEventResponse getOneByIdHireDesign(String id,TokenFindRequest tokenFindRequest);

    //Hiển thị location
    @Query(value = """
            SELECT el.id,el.event_id, el.name, el.formality, el.path 
            FROM event_location el
             LEFT JOIN event e on el.event_id = e.id 
             WHERE  el.event_id = ?1
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
            AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode} )
            """, countQuery = """
             SELECT COUNT(*)
             FROM event_location el
             LEFT JOIN event e on el.event_id = e.id 
             WHERE  el.event_id = ?1
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
            AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode} )
            """, nativeQuery = true)
    List<OhdlEventLocationResponse> getLocationOfHireDesign(String id,TokenFindRequest request);

    //hiển thị images event hire design
    @Query(value = """
            SELECT
             e.id, e.name, e.start_time, e.end_time, e.background_path, e.banner_path, e.standee_path
             FROM event e 
             WHERE  e.id = ?1
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
             AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode} )
            """, countQuery = """
            SELECT COUNT(*)         
                FROM event e 
                WHERE  e.id = ?1
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
                AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode} )
            """, nativeQuery = true)
    OhdlImageEventResponse getImageOfEvent(String id,TokenFindRequest request);
}
