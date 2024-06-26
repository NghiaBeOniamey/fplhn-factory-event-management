package com.portalevent.core.adminh.statisticseventmanagement.repository;


import com.portalevent.core.adminh.statisticseventmanagement.model.request.AdminHIdRequest;
import com.portalevent.core.adminh.statisticseventmanagement.model.request.AdminHSemesterMajorRequest;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseEventInMajor;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseEventInSemesterResponse;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseLecturerInEvent;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseListOrganizer;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseParticipantInEvent;
import com.portalevent.core.adminh.statisticseventmanagement.model.response.AdminHseTopEventResponse;
import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author HoangDV
 */
@Repository
public interface AdminHseEventRepository extends EventRepository {

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
                	e.semester_id = :#{#req.idSemester}
                	AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode} )
                	AND d.id = :#{#req.idDepartment}
            """, nativeQuery = true)
    Integer getSumEventBySemester(AdminHSemesterMajorRequest req, TokenFindRequest request);

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
                	e.semester_id = :#{#req.idSemester}
                    AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                	AND d.id = :#{#req.idDepartment}
                GROUP BY
                	e.status
            """, nativeQuery = true)
    List<AdminHseEventInSemesterResponse> getEventBySemester(AdminHSemesterMajorRequest req, TokenFindRequest request);

    @Query(value = """
            SELECT DISTINCT
            	    e.name AS name,
            	    e.number_participants AS numberParticipants
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
            	    e.semester_id = :#{#req.idSemester}
                    AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                    AND d.id = :#{#req.idDepartment}
            	    AND e.status = 5
                ORDER BY
            	    e.number_participants DESC
                LIMIT 3
            """, nativeQuery = true)
    List<AdminHseTopEventResponse> getTopEvent(AdminHSemesterMajorRequest req, TokenFindRequest request);

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
            	    e.semester_id = :#{#req.idSemester}
                    AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                    AND d.id = :#{#req.idDepartment}
            	    AND (e.event_type = 0 || event_type = 2)
                GROUP BY
            	    eo.organizer_id
                ORDER BY
            	    quantity_event DESC
            	LIMIT 10
            """, nativeQuery = true)
    List<AdminHseListOrganizer> getListOrganizer(AdminHSemesterMajorRequest req, TokenFindRequest request);

    @Query(value = """
            SELECT
                d.name,
                d.code,
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
                e.semester_id = :#{#req.idSemester}
                AND e.status = 5
                GROUP BY
                d.code,
                d.name
            """, nativeQuery = true)
    List<AdminHseEventInMajor> getEventInMajorByIdSemester(AdminHSemesterMajorRequest req);

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
                	e.semester_id = :#{#req.idSemester}
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                AND d.id = :#{#req.idDepartment}
                AND e.status = 5
                AND (event_type = 0 || event_type = 2)
                GROUP BY
                	e.name,
                	expected_participants,
                	number_participants
            """, nativeQuery = true)
    List<AdminHseParticipantInEvent> getListParticipantInEvent(AdminHSemesterMajorRequest req, TokenFindRequest request);

    @Query(value = """
            SELECT
            	e.name AS name,
            	COUNT(p.id) AS numberParticipantsEnrolled,
            	e.number_participants AS numberParticipants
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
            	e.semester_id = :#{#req.idSemester}
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                AND d.id = :#{#req.idDepartment}
            	AND e.status = 5
            GROUP BY
            	e.name,
            	number_participants
            """, nativeQuery = true)
    List<AdminHseLecturerInEvent> getListLecturerInEvent(AdminHSemesterMajorRequest req, TokenFindRequest request);

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
                	e.semester_id = :#{#req.idSemester}
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                AND d.id = :#{#req.idDepartment}
                AND e.category_id = :#{#req.idCategory}
                AND e.status = 5
                AND (event_type = 0 || event_type = 2)
                GROUP BY
                	e.name,
                	expected_participants,
                	number_participants
            """, nativeQuery = true)
    List<AdminHseParticipantInEvent> getListParticipantInEventByCategory(AdminHIdRequest req, TokenFindRequest request);

}
