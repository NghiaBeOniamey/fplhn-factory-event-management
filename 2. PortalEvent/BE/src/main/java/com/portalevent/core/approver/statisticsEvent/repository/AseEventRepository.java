package com.portalevent.core.approver.statisticsEvent.repository;


import com.portalevent.core.approver.statisticsEvent.model.response.AseEventInMajor;
import com.portalevent.core.approver.statisticsEvent.model.response.AseEventInSemesterResponse;
import com.portalevent.core.approver.statisticsEvent.model.response.AseLecturerInEvent;
import com.portalevent.core.approver.statisticsEvent.model.response.AseListOrganizer;
import com.portalevent.core.approver.statisticsEvent.model.response.AseParticipantInEvent;
import com.portalevent.core.approver.statisticsEvent.model.response.AseTopEventResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface AseEventRepository extends EventRepository {
    @Query(value = """     
                SELECT
                	COUNT(DISTINCT e.id) AS sumEvent
                FROM
                	event e
                LEFT JOIN event_major em ON
                	em.event_id = e.id
                LEFT JOIN major_campus mc ON
                	em.major_id = mc.id
                LEFT JOIN department_campus dc ON
                	mc.department_campus_id = dc.department_campus_id
                LEFT JOIN department d ON
                	dc.department_id = d.department_id
                LEFT JOIN campus c ON
                	dc.campus_id = c.campus_id
                WHERE
                	e.semester_id = :idSemester
                	AND (   :#{#req.currentTrainingFacilityCode} IS NULL OR
                	        :#{#req.currentTrainingFacilityCode} LIKE '' OR
                	        c.code = :#{#req.currentTrainingFacilityCode}  )
                	AND (   :#{#req.currentSubjectCode} IS NULL OR
                	        :#{#req.currentSubjectCode} LIKE '' OR
                	        d.code = :#{#req.currentSubjectCode}   )
            """, nativeQuery = true)
    Integer getSumEventBySemester(@Param("idSemester") String idSemester, TokenFindRequest req);

    @Query(value = """
                SELECT
                	e.status AS status,
                	COUNT(DISTINCT e.id) AS quantity
                FROM
                	event e
                LEFT JOIN event_major em ON
                	em.event_id = e.id
                LEFT JOIN major_campus mc ON
                	em.major_id = mc.id
                LEFT JOIN department_campus dc ON
                	mc.department_campus_id = dc.department_campus_id
                LEFT JOIN department d ON
                	dc.department_id = d.department_id
                LEFT JOIN campus c ON
                	dc.campus_id = c.campus_id
                WHERE
                	e.semester_id = :idSemester
                	AND (   :#{#req.currentTrainingFacilityCode} IS NULL OR
                	        :#{#req.currentTrainingFacilityCode} LIKE '' OR
                	        c.code = :#{#req.currentTrainingFacilityCode}  )
                	AND (   :#{#req.currentSubjectCode} IS NULL OR
                	        :#{#req.currentSubjectCode} LIKE '' OR
                	        d.code = :#{#req.currentSubjectCode}   )
                GROUP BY
                	e.status
            """, nativeQuery = true)
    List<AseEventInSemesterResponse> getEventBySemester(@Param("idSemester") String idSemester, TokenFindRequest req);

    @Query(value = """
            SELECT DISTINCT
            	    e.name AS name,
            	    number_participants AS numberParticipants
                FROM
            	    event e
                LEFT JOIN event_major em ON
            	    em.event_id = e.id
                LEFT JOIN major_campus mc ON
                	em.major_id = mc.id
                LEFT JOIN department_campus dc ON
            	    mc.department_campus_id = dc.department_campus_id
                LEFT JOIN department d ON
            	    dc.department_id = d.department_id
                LEFT JOIN campus c ON
            	    dc.campus_id = c.campus_id
                WHERE
            	    e.semester_id = :idSemester
                AND :#{#req.currentTrainingFacilityCode} IS NULL OR
                	:#{#req.currentTrainingFacilityCode} LIKE '' OR
                	c.code = :#{#req.currentTrainingFacilityCode}
                AND :#{#req.currentSubjectCode} IS NULL OR
                	:#{#req.currentSubjectCode} LIKE '' OR
                	d.code = :#{#req.currentSubjectCode}
            	AND e.status = 5
                ORDER BY
            	    number_participants DESC
                LIMIT 3
            """, nativeQuery = true)
    List<AseTopEventResponse> getTopEvent(@Param("idSemester") String idSemester, TokenFindRequest req);

    @Query(value = """
                SELECT
            	    eo.organizer_id AS organizer_id,
            	    COUNT(DISTINCT e.id) AS quantity_event
                FROM
            	    event e
                LEFT JOIN event_organizer eo ON
            	    eo.event_id = e.id
                LEFT JOIN event_major em ON
            	    em.event_id = e.id
                LEFT JOIN major_campus mc ON
                	em.major_id = mc.id
                LEFT JOIN department_campus dc ON
            	    mc.department_campus_id = dc.department_campus_id
                LEFT JOIN department d ON
            	    dc.department_id = d.department_id
                LEFT JOIN campus c ON
            	    dc.campus_id = c.campus_id
                WHERE
            	    e.semester_id = :idSemester
                	AND (   :#{#req.currentTrainingFacilityCode} IS NULL OR
                	        :#{#req.currentTrainingFacilityCode} LIKE '' OR
                	        c.code = :#{#req.currentTrainingFacilityCode}  )
                	AND (   :#{#req.currentSubjectCode} IS NULL OR
                	        :#{#req.currentSubjectCode} LIKE '' OR
                	        d.code = :#{#req.currentSubjectCode}   )
            	    AND (e.event_type = 0 || event_type = 2)
                GROUP BY
            	    eo.organizer_id
                ORDER BY
            	    quantity_event DESC
            	LIMIT 10
            """, nativeQuery = true)
    List<AseListOrganizer> getListOrganizer(@Param("idSemester") String id, TokenFindRequest req);

    @Query(value = """
                SELECT
                    m.name,
                    m.code,
                    COUNT(DISTINCT e.id) AS quantity
                FROM
                    event AS e
                    LEFT JOIN event_major em ON
                        em.event_id = e.id
                    LEFT JOIN major_campus mc ON
                        em.major_id = mc.id
                    LEFT JOIN major m ON
                        m.major_id = mc.major_id
                    LEFT JOIN department_campus dc ON
                        mc.department_campus_id = dc.department_campus_id
                    LEFT JOIN department d ON
                        dc.department_id = d.department_id
                    LEFT JOIN campus c ON
                        dc.campus_id = c.campus_id
                WHERE
                    e.semester_id = :idSemester
                	AND
                	    :#{#req.currentTrainingFacilityCode} IS NULL OR
                	    :#{#req.currentTrainingFacilityCode} LIKE '' OR
                	    c.code = :#{#req.currentTrainingFacilityCode}
                	AND
                	    :#{#req.currentSubjectCode} IS NULL OR
                	    :#{#req.currentSubjectCode} LIKE '' OR
                	    d.code = :#{#req.currentSubjectCode}
                    AND e.status = 5
                    GROUP BY
                    m.code,
                    m.name
            """, nativeQuery = true)
    List<AseEventInMajor> getEventInMajorByIdSemester(@Param("idSemester") String idSemester, TokenFindRequest req);

    @Query(value = """
            SELECT
            	e.name AS name,
            	COUNT(p.id) AS numberParticipantsEnrolled,
            	number_participants AS numberParticipants
            FROM
            	event e
            LEFT JOIN event_organizer eo ON
            	eo.event_id = e.id
            LEFT JOIN event_major em ON
            	em.event_id = e.id
            LEFT JOIN major_campus mc ON
            	em.major_id = mc.id
            LEFT JOIN department_campus dc ON
            	mc.department_campus_id = dc.department_campus_id
            LEFT JOIN department d ON
            	dc.department_id = d.department_id
            LEFT JOIN campus c ON
            	dc.campus_id = c.campus_id
            LEFT JOIN participant p ON
            	p.event_id = e.id
            WHERE
            	e.semester_id = :idSemester
                AND (   :#{#req.currentTrainingFacilityCode} IS NULL OR
                	:#{#req.currentTrainingFacilityCode} LIKE '' OR
                	c.code = :#{#req.currentTrainingFacilityCode}  )
                AND (   :#{#req.currentSubjectCode} IS NULL OR
                	:#{#req.currentSubjectCode} LIKE '' OR
                	d.code = :#{#req.currentSubjectCode}   )
            	AND e.status = 5
            GROUP BY
            	e.name,
            	number_participants
            """, nativeQuery = true)
    List<AseLecturerInEvent> getListLecturerInEvent(@Param("idSemester") String id, TokenFindRequest req);

    @Query(value = """
            SELECT
                	e.name AS name,
                	expected_participants AS expectedParticipants,
                	COUNT(p.id) AS numberParticipantsEnrolled,
                	number_participants AS numberParticipants
                FROM
                	event e
                LEFT JOIN event_organizer eo ON
                	eo.event_id = e.id
                LEFT JOIN event_major em ON
                	em.event_id = e.id
                LEFT JOIN major_campus mc ON
                	em.major_id = mc.id
                LEFT JOIN department_campus dc ON
                	mc.department_campus_id = dc.department_campus_id
                LEFT JOIN department d ON
                	dc.department_id = d.department_id
                LEFT JOIN campus c ON
                	dc.campus_id = c.campus_id
                LEFT JOIN participant p ON
                	p.event_id = e.id
                WHERE
                	e.semester_id = :idSemester
                AND (   :#{#req.currentTrainingFacilityCode} IS NULL OR
                	:#{#req.currentTrainingFacilityCode} LIKE '' OR
                	c.code = :#{#req.currentTrainingFacilityCode}  )
                AND (   :#{#req.currentSubjectCode} IS NULL OR
                	:#{#req.currentSubjectCode} LIKE '' OR
                	d.code = :#{#req.currentSubjectCode}   )
                AND e.status = 5
                AND (event_type = 0 || event_type = 2)
                GROUP BY
                	e.name,
                	expected_participants,
                	number_participants
            """, nativeQuery = true)
    List<AseParticipantInEvent> getListParticipantInEvent(@Param("idSemester") String id, TokenFindRequest req);

    @Query(value = """
                SELECT
                	e.name AS name,
                	expected_participants AS expectedParticipants,
                	COUNT(p.id) AS numberParticipantsEnrolled,
                	number_participants AS numberParticipants
                FROM
                	event e
                LEFT JOIN event_organizer eo ON
                	eo.event_id = e.id
                LEFT JOIN event_major em ON
                	em.event_id = e.id
                LEFT JOIN major_campus mc ON
                	em.major_id = mc.id
                LEFT JOIN department_campus dc ON
                	mc.department_campus_id = dc.department_campus_id
                LEFT JOIN department d ON
                	dc.department_id = d.department_id
                LEFT JOIN campus c ON
                	dc.campus_id = c.campus_id
                LEFT JOIN participant p ON
                	p.event_id = e.id
                WHERE
                	e.semester_id = :idSemester
                AND (   :#{#req.currentTrainingFacilityCode} IS NULL OR
                	:#{#req.currentTrainingFacilityCode} LIKE '' OR
                	c.code = :#{#req.currentTrainingFacilityCode}  )
                AND (   :#{#req.currentSubjectCode} IS NULL OR
                	:#{#req.currentSubjectCode} LIKE '' OR
                	d.code = :#{#req.currentSubjectCode}   )
                AND e.category_id = :idCategory
                AND e.status = 5
                AND (event_type = 0 || event_type = 2)
                GROUP BY
                	e.name,
                	expected_participants,
                	number_participants
            """, nativeQuery = true)
    List<AseParticipantInEvent> getListParticipantInEventByCategory(@Param("idSemester") String id, @Param("idCategory") String idCategory, TokenFindRequest req);
}
