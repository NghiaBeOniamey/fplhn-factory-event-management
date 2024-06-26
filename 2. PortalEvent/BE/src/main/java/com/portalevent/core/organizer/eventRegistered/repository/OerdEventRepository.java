package com.portalevent.core.organizer.eventRegistered.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.eventRegistered.model.request.OerdFilterEventRequest;
import com.portalevent.core.organizer.eventRegistered.model.response.OerdEventResponse;
import com.portalevent.core.organizer.eventRegistered.model.response.OerdLocationResponse;
import com.portalevent.repository.EventRepository;
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
public interface OerdEventRepository extends EventRepository {
    @Query(value = """
            SELECT ev.id, ev.name, ev.start_time, ev.end_time,
            ROW_NUMBER() OVER(ORDER BY ev.start_time DESC) AS indexs,
            ev.description, ev.category_id, ev.status, ev.background_path, ev.standee_path
            FROM(
                SELECT a.id, a.name, a.start_time, a.end_time,
                a.description, a.category_id, a.status, a.background_path, a.standee_path
                FROM event a JOIN event_organizer b ON a.id = b.event_id
                LEFT JOIN event_location el ON a.id = el.event_id
                LEFT JOIN event_major em ON
                	em.event_id = a.id
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
                WHERE b.organizer_id = :#{#req.idOrganizer}
                AND (:#{#req.idSemester} LIKE '' OR :#{#req.idSemester} IS NULL OR a.semester_id = :#{#req.idSemester})
                AND (:#{#req.formality} LIKE '' OR :#{#req.formality} IS NULL OR el.formality = :#{#req.formality})
                AND (a.event_type <> 3 OR a.event_type IS NULL)
                AND (:#{#request.currentSubjectCode} IS NULL OR d.code LIKE :#{#request.currentSubjectCode})
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                GROUP BY a.id, a.start_time
                ORDER BY a.start_time DESC
                ) ev
                WHERE (:#{#req.status} LIKE '' OR :#{#req.status} IS NULL OR ev.status = :#{#req.status})
                AND(:#{#req.name} LIKE '' OR :#{#req.name} IS NULL OR ev.name LIKE :#{'%'+#req.name+'%'})
                AND (:#{#req.idCategory} LIKE '' OR :#{#req.idCategory} IS NULL OR ev.category_id = :#{#req.idCategory})
                AND (:#{#req.startTime} LIKE '' OR :#{#req.startTime} IS NULL
                OR :#{#req.endTime} LIKE '' OR :#{#req.endTime} IS NULL
                OR (ev.start_time >= :#{#req.startTime} AND ev.end_time <= :#{#req.endTime}))
                GROUP BY ev.id, ev.start_time
                ORDER BY
                    CASE WHEN :#{#req.statusSort} = 0 THEN ev.start_time END,
                    CASE WHEN :#{#req.statusSort} = 1 THEN ev.start_time END DESC
            """,
            countQuery = """
                    SELECT COUNT(*)
                    FROM(
                        SELECT a.id, a.name, a.start_time, a.end_time,
                        a.description, a.category_id, a.status, a.background_path, a.standee_path
                        FROM event a JOIN event_organizer b ON a.id = b.event_id
                        LEFT JOIN event_location el ON a.id = el.event_id
                        LEFT JOIN event_major em ON
                            em.event_id = a.id
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
                        WHERE b.organizer_id = :#{#req.idOrganizer}
                        AND (:#{#req.idSemester} LIKE '' OR :#{#req.idSemester} IS NULL OR a.semester_id = :#{#req.idSemester})
                        AND (:#{#req.formality} LIKE '' OR :#{#req.formality} IS NULL OR el.formality = :#{#req.formality})
                        AND (a.event_type <> 3 OR a.event_type IS NULL)
                        AND (:#{#request.currentSubjectCode} IS NULL OR d.code LIKE :#{#request.currentSubjectCode})
                        AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                        GROUP BY a.id, a.start_time
                        ORDER BY
                            a.start_time DESC
                        ) ev
                        WHERE (:#{#req.status} LIKE '' OR :#{#req.status} IS NULL OR ev.status = :#{#req.status})
                        AND(:#{#req.name} LIKE '' OR :#{#req.name} IS NULL OR ev.name LIKE :#{'%'+#req.name+'%'})
                        AND (:#{#req.idCategory} LIKE '' OR :#{#req.idCategory} IS NULL OR ev.category_id = :#{#req.idCategory})
                        AND (:#{#req.startTime} LIKE '' OR :#{#req.startTime} IS NULL
                        OR :#{#req.endTime} LIKE '' OR :#{#req.endTime} IS NULL
                        OR (ev.start_time >= :#{#req.startTime} AND ev.end_time <= :#{#req.endTime}))
                        GROUP BY ev.id, ev.start_time
                        ORDER BY
                            CASE WHEN :#{#req.statusSort} = 0 THEN ev.start_time END,
                            CASE WHEN :#{#req.statusSort} = 1 THEN ev.start_time END DESC
                    """,nativeQuery = true)
    Page<OerdEventResponse> getAllEventByIdOrganizer(Pageable pageable, @Param("req") OerdFilterEventRequest req, TokenFindRequest request);

    @Query(value = """
            SELECT ev.id, ev.name, ev.start_time, ev.end_time,
            ROW_NUMBER() OVER(ORDER BY ev.start_time DESC) AS indexs,
            ev.description, ev.category_id, ev.status, ev.background_path, ev.standee_path
            FROM(
                SELECT a.id, a.name, a.start_time, a.end_time,
                a.description, a.category_id, a.status, a.background_path, a.standee_path
                FROM event a JOIN event_organizer b ON a.id = b.event_id
                LEFT JOIN event_location el ON a.id = el.event_id
                LEFT JOIN event_major em ON
                    em.event_id = a.id
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
                (:#{#req.idSemester} LIKE '' OR :#{#req.idSemester} IS NULL OR a.semester_id = :#{#req.idSemester})
                AND (:#{#req.formality} LIKE '' OR :#{#req.formality} IS NULL OR el.formality = :#{#req.formality})
                AND (a.event_type <> 3 OR a.event_type IS NULL)
                AND (:#{#request.currentSubjectCode} IS NULL OR d.code LIKE :#{#request.currentSubjectCode})
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                GROUP BY a.id, a.start_time
                ORDER BY a.start_time DESC
                ) ev
                WHERE (:#{#req.status} LIKE '' OR :#{#req.status} IS NULL OR ev.status = :#{#req.status})
                AND(:#{#req.name} LIKE '' OR :#{#req.name} IS NULL OR ev.name LIKE :#{'%'+#req.name+'%'})
                AND (:#{#req.idCategory} LIKE '' OR :#{#req.idCategory} IS NULL OR ev.category_id = :#{#req.idCategory})
                AND (:#{#req.startTime} LIKE '' OR :#{#req.startTime} IS NULL
                OR :#{#req.endTime} LIKE '' OR :#{#req.endTime} IS NULL
                OR (ev.start_time >= :#{#req.startTime} AND ev.end_time <= :#{#req.endTime}))
                GROUP BY ev.id, ev.start_time
                ORDER BY
                    CASE WHEN :#{#req.statusSort} = 0 THEN ev.start_time END,
                    CASE WHEN :#{#req.statusSort} = 1 THEN ev.start_time END DESC
            """,
            countQuery = """
            SELECT COUNT(*)
            FROM(
                SELECT a.id, a.name, a.start_time, a.end_time,
                a.description, a.category_id, a.status, a.background_path, a.standee_path
                FROM event a JOIN event_organizer b ON a.id = b.event_id
                LEFT JOIN event_location el ON a.id = el.event_id
                LEFT JOIN event_major em ON
                    em.event_id = a.id
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
                (:#{#req.idSemester} LIKE '' OR :#{#req.idSemester} IS NULL OR a.semester_id = :#{#req.idSemester})
                AND (:#{#req.formality} LIKE '' OR :#{#req.formality} IS NULL OR el.formality = :#{#req.formality})
                AND (a.event_type <> 3 OR a.event_type IS NULL)
                AND (:#{#request.currentSubjectCode} IS NULL OR d.code LIKE :#{#request.currentSubjectCode})
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR c.code LIKE :#{#request.currentTrainingFacilityCode})
                GROUP BY a.id, a.start_time
                ORDER BY
                    a.start_time DESC
                ) ev
                WHERE (:#{#req.status} LIKE '' OR :#{#req.status} IS NULL OR ev.status = :#{#req.status})
                AND(:#{#req.name} LIKE '' OR :#{#req.name} IS NULL OR ev.name LIKE :#{'%'+#req.name+'%'})
                AND (:#{#req.idCategory} LIKE '' OR :#{#req.idCategory} IS NULL OR ev.category_id = :#{#req.idCategory})
                AND (:#{#req.startTime} LIKE '' OR :#{#req.startTime} IS NULL
                OR :#{#req.endTime} LIKE '' OR :#{#req.endTime} IS NULL
                OR (ev.start_time >= :#{#req.startTime} AND ev.end_time <= :#{#req.endTime}))
                GROUP BY ev.id, ev.start_time
                ORDER BY
                    CASE WHEN :#{#req.statusSort} = 0 THEN ev.start_time END,
                    CASE WHEN :#{#req.statusSort} = 1 THEN ev.start_time END DESC
            """,nativeQuery = true)
    Page<OerdEventResponse> getAllEventIsMe(Pageable pageable, @Param("req") OerdFilterEventRequest req, TokenFindRequest request);

    @Query(value = """
            SELECT el.id, el.name, el.formality, el.path FROM portal_event.event_location el
            join event e on el.event_id = e.id
            where e.id = :#{#idEvent}
            group by el.id
            """, nativeQuery = true)
    List<OerdLocationResponse> getLocationByEventId(String idEvent );

}
