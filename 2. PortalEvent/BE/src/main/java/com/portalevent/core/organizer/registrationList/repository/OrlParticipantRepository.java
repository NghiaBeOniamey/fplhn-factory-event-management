package com.portalevent.core.organizer.registrationList.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.registrationList.model.request.OrlFindQuestionsRequest;
import com.portalevent.core.organizer.registrationList.model.response.OrlQuestionResponse;
import com.portalevent.core.organizer.registrationList.model.response.OrlRegistrationResponse;
import com.portalevent.repository.ParticipantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author SonPT
 */

@Repository
public interface OrlParticipantRepository extends ParticipantRepository {

    @Query(value = """
            SELECT a.id as id,
                   ROW_NUMBER() OVER(ORDER BY a.created_date DESC) AS indexs,
                   a.email as email,
                   a.participant_code as participantCode,
                   a.participant_name as participantName,
                   a.created_date AS createDate,
                   CASE 
                       WHEN a.role LIKE '%SINH_VIEN%' THEN 'Sinh viên'
                       ELSE 'Giảng viên'
                       END AS role
            FROM participant a
            WHERE a.event_id = :#{#req.idEvent}
            AND a.participant_type = 0
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.participantCode} LIKE '' OR :#{#req.participantCode} IS NULL OR a.participant_code LIKE %:#{#req.participantCode}%)
            AND (:#{#req.participantName} LIKE '' OR :#{#req.participantName} IS NULL OR a.participant_name LIKE %:#{#req.participantName}%)
            AND (:#{#req.startTimeSearch} LIKE ''
                 OR :#{#req.endTimeSearch} LIKE ''
                 OR :#{#req.startTimeSearch} IS NULL
                 OR :#{#req.endTimeSearch} IS NULL
                 OR (a.created_date > :#{#req.startTimeSearch} AND a.created_date < :#{#req.endTimeSearch} ))
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR a.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
            AND (:#{#request.currentSubjectCode} IS NULL OR a.subject_code LIKE :#{#request.currentSubjectCode} )
            ORDER BY a.created_date DESC
            """, countQuery = """
            SELECT COUNT(*) FROM participant a
            WHERE a.event_id = :#{#req.idEvent}
            AND a.participant_type = 0
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.participantCode} LIKE '' OR :#{#req.participantCode} IS NULL OR a.participant_code LIKE %:#{#req.participantCode}%)
            AND (:#{#req.participantName} LIKE '' OR :#{#req.participantName} IS NULL OR a.participant_name LIKE %:#{#req.participantName}%)
            AND (:#{#req.startTimeSearch} LIKE ''
                 OR :#{#req.endTimeSearch} LIKE ''
                 OR :#{#req.startTimeSearch} IS NULL
                 OR :#{#req.endTimeSearch} IS NULL
                 OR (a.created_date > :#{#req.startTimeSearch} AND a.created_date < :#{#req.endTimeSearch} ))
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR a.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
            AND (:#{#request.currentSubjectCode} IS NULL OR a.subject_code LIKE :#{#request.currentSubjectCode} )
            ORDER BY a.created_date DESC
            """, nativeQuery = true)
    Page<OrlQuestionResponse> getListResgistration(Pageable pageable, @Param("req") OrlFindQuestionsRequest req, TokenFindRequest request);

    @Query(value = """
            SELECT COUNT(1) FROM participant a
            WHERE a.event_id = :#{#req.idEvent}
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.question} LIKE '' OR :#{#req.question} IS NULL OR a.question LIKE %:#{#req.question}%)
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR a.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
            AND (:#{#request.currentSubjectCode} IS NULL OR a.subject_code LIKE :#{#request.currentSubjectCode} )
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    Integer countAllSearchQuestion(@Param("req") OrlFindQuestionsRequest req, TokenFindRequest request);

    @Query(value = """
            SELECT 
            	p.participant_code as code,
            	p.participant_name as name
            FROM
            	participant p
            WHERE 
            	p.participant_type = 0
            	AND p.event_id = :#{#idEvent}
                        """, nativeQuery = true)
    List<OrlRegistrationResponse> getRegistrationList(String idEvent);
}
