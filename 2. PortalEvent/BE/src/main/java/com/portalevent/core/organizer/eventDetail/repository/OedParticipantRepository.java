package com.portalevent.core.organizer.eventDetail.repository;

import com.portalevent.core.common.TokenFindRequest;
import com.portalevent.core.organizer.attendanceList.model.response.OalAttendanceResponse;
import com.portalevent.repository.ParticipantRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OedParticipantRepository extends ParticipantRepository {

    @Query(value = """
               SELECT count(*) FROM participant
               WHERE event_id = :idEvent
               AND participant_type =1
            """, nativeQuery = true)
    Integer countByEventId(@Param("idEvent") String idEvent );
    @Query(value = """
        SELECT SUM(rate)
        FROM participant a
        WHERE a.event_id = :idEvent
    """, nativeQuery = true)
    Integer sumRateByEventId(@Param("idEvent") String eventId );

    @Query(value = """
        SELECT SUM(rate)
        FROM participant a WHERE a.event_id = :idEvent 
        AND a.rate IN (4, 5)
    """, nativeQuery = true)
    Integer sumRate45ByEventId(@Param("idEvent") String eventId );

    @Query(value = """
               SELECT count(*) FROM participant
               WHERE event_id = :idEvent
               AND participant_type =0
            """, nativeQuery = true)
    Integer countRateParticipant(@Param("idEvent") String idEvent );

    @Query(value = """
            SELECT a.id, 
                ROW_NUMBER() OVER(ORDER BY a.created_date DESC) AS stt, 
                a.email, 
                a.class_name, 
                a.attendance_time, 
                a.rate, 
                a.feedback 
            FROM participant a
            WHERE a.event_id = :idEvent
            AND a.attendance_time IS NOT NULL
    """, nativeQuery = true)
    List<OalAttendanceResponse> getAllAttendanceByEventId(@Param("idEvent") String eventId );
}

