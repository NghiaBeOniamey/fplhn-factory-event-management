package com.portalevent.core.organizer.statisticsEvent.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.repository.EventOrganizerRepository;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author HoangDV
 */
@Repository
public interface OseEventOrganizerRepository extends EventOrganizerRepository {
    @Query(value = """
                SELECT eo.event_role FROM event_organizer AS eo
                JOIN event AS e ON e.id = eo.event_id
                WHERE e.semester_id = :idSemester
                AND (:#{#request.currentTrainingFacilityCode} IS NULL OR e.training_facility_code LIKE :#{#request.currentTrainingFacilityCode})
                AND (:#{#request.currentSubjectCode} IS NULL OR e.subject_code LIKE :#{#request.currentSubjectCode})
                AND eo.organizer_id = :userCode
                AND (e.status = 5 || 
                (UNIX_TIMESTAMP(NOW()) * 1000 >= e.start_time 
                AND UNIX_TIMESTAMP(NOW()) * 1000 <= e.end_time))
                """, nativeQuery = true)
    List<Integer> getRoleUserInEventInSemester(@Param("idSemester") String idSemester, @Param("userCode") String userCode, TokenFindRequest request);
}
