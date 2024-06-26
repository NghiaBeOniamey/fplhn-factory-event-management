package com.portalevent.core.organizer.statisticsEvent.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseEventInSemesterResponse;
import com.portalevent.core.organizer.statisticsEvent.model.response.OseTopEventResponse;
import com.portalevent.repository.EventRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface OseEventRepository extends EventRepository {
    @Query(value = """
            SELECT e.id, e.name, e.number_participants, eo.event_role FROM event e
            JOIN event_organizer eo ON eo.event_id = e.id
            WHERE e.semester_id = :idSemester
            AND e.status = 5
            AND eo.organizer_id LIKE :codeOrganizer
            ORDER BY e.number_participants DESC
            LIMIT 3
            """, nativeQuery = true)
    List<OseTopEventResponse> getTopEvent(@Param("idSemester") String idSemester, String codeOrganizer);

    @Query(value = """
                SELECT e.status, COUNT(e.id) AS quantity FROM event AS e
                JOIN event_organizer AS eo ON eo.event_id = e.id
                WHERE 
                eo.organizer_id LIKE :codeOrganizer
                AND e.semester_id = :idSemester
                GROUP BY e.status
            """, nativeQuery = true)
    List<OseEventInSemesterResponse> getEventBySemesterAndOrganizer(String codeOrganizer, @Param("idSemester") String idSemester  );

    @Query(value = """
                SELECT COUNT(e.id) AS sumEvent FROM event e
                JOIN event_organizer eo ON eo.event_id = e.id
                WHERE e.semester_id = :idSemester
                AND eo.organizer_id LIKE :codeOrganizer
            """, nativeQuery = true)
    Integer getSumEventBySemesterAndOrganizer(String codeOrganizer, @Param("idSemester") String idSemester);
}
