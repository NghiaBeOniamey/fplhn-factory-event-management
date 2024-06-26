package com.portalevent.core.approver.eventattendancelist.repository;

import com.portalevent.core.approver.eventattendancelist.model.request.AealEventAttendanceSearchRequest;
import com.portalevent.core.approver.eventattendancelist.model.response.AealEventAttendanceListResponse;
import com.portalevent.core.approver.eventattendancelist.model.response.ArlAttendanceResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.ParticipantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AealEventAttendanceRepository extends ParticipantRepository {

    @Query(value = """
            SELECT  ROW_NUMBER() OVER (ORDER BY p.email) AS indexs,
                p.email as email,
                p.class_name as className,
                p.attendance_time as attendanceTime,
                p.rate as rate,
                p.feedback as feedback
           FROM participant AS p
           WHERE p.event_id = :idEvent
    """, nativeQuery = true)
    List<AealEventAttendanceListResponse> getAttendanceListByIdEvent(@Param("idEvent") String id);

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
            AND a.participant_type = 1
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
            AND a.participant_type = 1
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
    Page<AealEventAttendanceListResponse> getAllAttendance(Pageable pageable, @Param("req") AealEventAttendanceSearchRequest req, TokenFindRequest request);

    @Query(value = """
            SELECT COUNT(1) FROM participant a
            WHERE a.event_id = :#{#req.idEvent} 
            AND (:#{#req.email} LIKE '' OR :#{#req.email} IS NULL OR a.email LIKE %:#{#req.email}%)
            AND (:#{#req.className} LIKE '' OR :#{#req.className} IS NULL OR a.class_name LIKE %:#{#req.className}%)
            AND (:#{#req.rate} LIKE '' OR :#{#req.rate} IS NULL OR FIND_IN_SET(a.rate,:#{#req.rate})) 
            AND (:#{#req.feedback} LIKE '' OR :#{#req.feedback} IS NULL OR a.feedback LIKE %:#{#req.feedback}%) 
            AND (:#{#request.currentTrainingFacilityCode} IS NULL OR a.training_facility_code LIKE :#{#request.currentTrainingFacilityCode} )
            AND (:#{#request.currentSubjectCode} IS NULL OR a.subject_code LIKE :#{#request.currentSubjectCode})
            AND a.attendance_time IS NOT NULL
            ORDER BY a.created_date DESC 
            """, nativeQuery = true)
    Integer countAllSearch(@Param("req") AealEventAttendanceSearchRequest req,TokenFindRequest request);

    @Query(value = """
            SELECT 
            	p.participant_code as code,
            	p.participant_name as name,
            	e.name AS eventName
            FROM
            	participant p
                LEFT JOIN event e ON p.event_id = e.id
            WHERE 
            	p.participant_type = 1
            	AND p.event_id = :#{#idEvent}
                        """, nativeQuery = true)
    List<ArlAttendanceResponse> getAttendanceList(String idEvent);
}
